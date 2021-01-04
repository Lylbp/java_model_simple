package com.lylbp.manager.hbase.service;

import cn.hutool.core.util.StrUtil;
import com.lylbp.manager.hbase.annotion.HTable;
import com.lylbp.manager.hbase.converter.DefaultConversionService;
import com.lylbp.manager.hbase.entity.HClazzMapping;
import com.lylbp.manager.hbase.entity.HPage;
import com.lylbp.manager.hbase.handler.DefaultHandler;
import com.lylbp.manager.hbase.handler.FieldMapper;
import com.lylbp.manager.hbase.handler.exception.HandlerException;
import com.lylbp.manager.hbase.handler.exception.HbaseAnnotationException;
import com.lylbp.manager.hbase.util.HBaseUtil;
import com.lylbp.manager.hbase.handler.HRowHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * HbaseService
 *
 * @author weiwenbin
 * @date 2020/11/2 下午1:34
 */
@Slf4j
@Service
@Data
public class HBaseBeanService<T> {
    @Resource
    private Connection connection;

    @Resource
    private Admin hBaseAdmin;

    private DefaultHandler<T> defaultHandler = new DefaultHandler<>(new DefaultConversionService());

    //////////////////////////////////////////1对表进行操作////////////////////////////////////////////////////////////

    /**
     * 判断表是否存在
     *
     * @param clazz clazz
     * @return true/false
     */
    public boolean isExists(Class<T> clazz) throws InstantiationException, HbaseAnnotationException {
        return isExists(getTableName(clazz));
    }

    /**
     * 判断表是否存在
     *
     * @param tableName 表名
     * @return true/false
     */
    public boolean isExists(TableName tableName) {
        boolean tableExists = false;
        try {
            tableExists = hBaseAdmin.tableExists(tableName);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return tableExists;
    }

    /**
     * 创建表
     *
     * @param clazz clazz
     * @return boolean
     */
    public boolean createTable(Class<T> clazz) throws Exception {
        HClazzMapping<T> hClazzMapping = getHClazzMapping(clazz);
        TableName tableName = hClazzMapping.getTableName();
        List<FieldMapper<T>> columnMappers = hClazzMapping.getRowHandler().getColumnMappers();

        List<String> list = new ArrayList<>(10);
        for (FieldMapper<T> fieldMapper : columnMappers) {
            String columnFamily = Bytes.toString(fieldMapper.getFamily());
            if (!list.contains(columnFamily)){
                list.add(columnFamily);
            }
        }

        return createTable(tableName, list, null);
    }

    /**
     * 创建表
     *
     * @param tableName    表名
     * @param columnFamily 列簇
     * @return true/false
     */
    public boolean createTable(TableName tableName, List<String> columnFamily) throws Exception {
        return createTable(tableName, columnFamily, null);
    }

    /**
     * 预分区创建表
     *
     * @param tableName    表名
     * @param columnFamilies 列簇
     * @param keys         分区集合
     * @return true/false
     */
    public boolean createTable(TableName tableName, List<String> columnFamilies, List<String> keys) throws Exception {
        if (isExists(tableName)) {
            throw new Exception("表名已存在");
        }

        try {
            //表描述器构造器
            TableDescriptorBuilder tdb = TableDescriptorBuilder.newBuilder(tableName);
            for (String cf : columnFamilies) {
                //列簇描述器构造器
                ColumnFamilyDescriptorBuilder cdb = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf));
                //获得列描述器
                ColumnFamilyDescriptor cfd = cdb.build();
                //添加列簇
                tdb.setColumnFamily(cfd);
            }
            //获得表描述器
            TableDescriptor td = tdb.build();
            //创建表
            if (keys == null) {
                hBaseAdmin.createTable(td);
            } else {
                byte[][] splitKeys = HBaseUtil.getSplitKeys(keys);
                hBaseAdmin.createTable(td, splitKeys);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 删除表
     *
     * @param clazz 表名
     */
    public Boolean dropTable(Class<T> clazz) throws Exception {
        return dropTable(getTableName(clazz));
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    public Boolean dropTable(TableName tableName) throws Exception {
        if (!isExists(tableName)) {
            throw new Exception("表不已存在");
        }
        hBaseAdmin.disableTable(tableName);
        hBaseAdmin.deleteTable(tableName);

        return true;
    }

    //////////////////////////////////////////2.增////////////////////////////////////////////////////////////
    //////////////////////////////////////////2.1使用对象操作////////////////////////////////////////////////////////////////

    /**
     * 单条数据插入
     *
     * @param entity 实体类
     */
    public void ePutData(T entity) throws HbaseAnnotationException, IOException, HandlerException, InstantiationException {
        eBatchPutData(Collections.singletonList(entity));
    }

    /**
     * 多条数据插入
     *
     * @param entities 实体类集合
     */
    public void eBatchPutData(List<T> entities) throws HbaseAnnotationException, IOException, HandlerException, InstantiationException {
        HClazzMapping<T> hClazzMapping = getHClazzMapping((Class<T>) entities.get(0).getClass());
        TableName tableName = hClazzMapping.getTableName();
        HRowHandler<T> rowHandler = hClazzMapping.getRowHandler();

        List<Put> puts = new ArrayList<>(10);
        for (T entity : entities) {
            Put put = defaultHandler.buildPut(entity, rowHandler);
            puts.add(put);
        }

        Table table = connection.getTable(tableName);
        table.put(puts);
        table.close();
    }

    //////////////////////////////////////////2.2不使用对象操作////////////////////////////////////////////////////////////

    /**
     * 插入单条-单列簇-多列数据数据
     * 注意！！column与value中的值必须一一对应
     *
     * @param tableName    表名
     * @param rowKey       rowKey
     * @param columnFamily 列簇
     * @param column       列
     * @param value        值
     * @return boolean
     */
    public boolean mPutData(TableName tableName, String rowKey, String columnFamily, String column, String value) {
        return mBatchPutData(tableName, rowKey, columnFamily, Collections.singletonList(column),
                Collections.singletonList(value));
    }

    /**
     * 插入多条-单列簇-多列数据
     *
     * @param tableName    表名
     * @param rowKey       rowKey
     * @param columnFamily 列簇
     * @param columns      列
     * @param values       值
     * @return boolean
     */
    public boolean mBatchPutData(TableName tableName, String rowKey, String columnFamily, List<String> columns,
                                 List<String> values) {
        try {
            Table table = hBaseAdmin.getConnection().getTable(tableName);
            Put put = new Put(Bytes.toBytes(rowKey));
            for (int i = 0; i < columns.size(); i++) {
                String column = columns.get(i);
                String value = values.get(i);
                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
            }
            table.put(put);
            table.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    //////////////////////////////////////////3删除////////////////////////////////////////////////////////////

    //////////////////////////////////////////3.1使用对象操作////////////////////////////////////////////////////////////////


    //////////////////////////////////////////3.2不使用对象操作///////////////////////////////////////////////////////////////

    /**
     * 根据rowKey删除数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     */
    public void mDeleteByRowKey(TableName tableName, String rowKey) throws IOException {
        mBatchDeleteByRowKey(tableName, Collections.singletonList(rowKey));
    }

    /**
     * 根据rowKey集合批量删除数据
     *
     * @param tableName 表名
     * @param rowKeys   rowKey集合
     */
    public void mBatchDeleteByRowKey(TableName tableName, List<String> rowKeys) throws IOException {
        Table table = connection.getTable(tableName);

        List<Delete> deleteList = new ArrayList<>(10);
        for (String row : rowKeys) {
            Delete delete = new Delete(Bytes.toBytes(row));
            deleteList.add(delete);
        }
        table.delete(deleteList);
        table.close();
    }

    /**
     * 根据rowKey、列簇删除数据
     *
     * @param tableName    表名
     * @param rowKey       rowKey
     * @param columnFamily 列簇
     */
    public void mDeleteByRowKeyAndColumnFamily(TableName tableName, String rowKey, String columnFamily) throws IOException {
        Table table = connection.getTable(tableName);

        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.addFamily(columnFamily.getBytes());
        table.delete(delete);
        table.close();
    }

    /**
     * 根据rowKey，列簇、列删除数据
     *
     * @param tableName    表名
     * @param rowKey       rowKey
     * @param columnFamily 列簇
     * @param column       列
     */
    public void mDeleteByRowKeyAndColumnFamilyAndColumn(TableName tableName, String rowKey, String columnFamily, String column)
            throws IOException {
        Table table = connection.getTable(tableName);

        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.addColumn(columnFamily.getBytes(), column.getBytes());
        table.delete(delete);
        table.close();
    }


    //////////////////////////////////////////4.改////////////////////////////////////////////////////////////

    //////////////////////////////////////////5.查////////////////////////////////////////////////////////////
    //////////////////////////////////////////5.1使用对象操作/////////////////////////////////////////////////////////////
    /**
     * 获取数据
     * rowKey过滤器 str$ 末尾匹配，相当于sql中的 %str  ^str开头匹配，相当于sql中的str%
     * [LESS:< ,LESS_OR_EQUAL:<=,EQUAL:=,NOT_EQUAL:<>,GREATER_OR_EQUAL:>=,GREATER:>,NO_OP 排除所有]
     *
     * @param clazz      clazz
     * @param page       分页参数
     * @param filterList 过滤器集合
     */
    public List<T> eGetPageData(Class<T> clazz, HPage<T> page, FilterList filterList) throws Throwable {
        List<T> list = new ArrayList<>(10);
        HClazzMapping<T> hClazzMapping = getHClazzMapping(clazz);
        TableName tableName = hClazzMapping.getTableName();
        HRowHandler<T> rowHandler = hClazzMapping.getRowHandler();

        //获取表
        Table table = connection.getTable(tableName);

        Scan scan = getScan(filterList);

        if (null != page) {
            int pageNum = (int) page.getCurrent();
            int pageSize = (int) page.getSize();
            //获取总数
            long total = enableAggregation(tableName, filterList);
            page.setTotal(total);
            //获取lastRowKey
            byte[] lastRowKey = getLastRowKey(table, page, filterList);
            //查询数据
            filterList.addFilter(new PageFilter(pageNum == 1 ? pageSize : pageSize + 1));
            if (null != lastRowKey) {
                scan.withStartRow(lastRowKey);
            }
        }

        //查询
        ResultScanner resultScanner = table.getScanner(scan);

        //分页并为第一页去除一条
        if (null != page && (int) page.getCurrent() != 1) {
            resultScanner.next(1);
        }

        //封装结果集
        for (Result result : resultScanner) {
            T convert = defaultHandler.convert(result, clazz, rowHandler);
            list.add(convert);
        }
        //分页设置记录
        if (null != page) {
            page.setRecords(list);
        }

        return list;
    }


    public List<T> getByRowKeyList(Class<T> clazz, List<String> rowKeyList) throws Throwable {
        List<T> list = new ArrayList<>(10);
        List<Get> getList = new ArrayList<>(10);
        HClazzMapping<T> hClazzMapping = getHClazzMapping(clazz);
        TableName tableName = hClazzMapping.getTableName();
        HRowHandler<T> rowHandler = hClazzMapping.getRowHandler();

        // 获取表
        Table table = connection.getTable(tableName);
        //getList
        for (String rowkey : rowKeyList) {
            Get get = new Get(Bytes.toBytes(rowkey));
            getList.add(get);
        }
        //查询
        Result[] results = table.get(getList);
        //对返回的结果集进行操作
        for (Result result : results) {
            T convert = defaultHandler.convert(result, clazz, rowHandler);
            list.add(convert);
        }

        return list;

    }

    //////////////////////////////////////////5.2不使用对象操作////////////////////////////////////////////////////////////

    /**
     * 获取全表数据
     *
     * @param tableName 表名
     */
    public List<Map<String, String>> mGetData(TableName tableName) throws IOException {
        return mGetData(tableName, null);
    }

    /**
     * 获取全表数据
     * rowKey过滤器 str$ 末尾匹配，相当于sql中的 %str  ^str开头匹配，相当于sql中的str%
     * [LESS:< ,LESS_OR_EQUAL:<=,EQUAL:=,NOT_EQUAL:<>,GREATER_OR_EQUAL:>=,GREATER:>,NO_OP 排除所有]
     *
     * @param tableName  表名
     * @param filterList 过滤器集合
     */
    public List<Map<String, String>> mGetData(TableName tableName, FilterList filterList) throws IOException {
        Table table = connection.getTable(tableName);

        //获取scan
        Scan scan = getScan(filterList);
        //查询
        ResultScanner resultScanner = table.getScanner(scan);
        //转map
        List<Map<String, String>> list = HBaseUtil.resultScanner2MapList(resultScanner);
        table.close();

        return list;
    }

    /**
     * 根据rowKey获取数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return map
     */
    public Map<String, String> mGetDataByRowKey(TableName tableName, String rowKey) throws IOException {
        Table table = connection.getTable(tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);
        Map<String, String> map = HBaseUtil.result2Map(result);
        table.close();
        return map;
    }

    /**
     * 根据对应rowKey、列簇、列的值
     *
     * @param tableName       表名
     * @param rowKey          rowKey
     * @param columnFamily    列簇
     * @param columnQualifier 列
     * @return String
     */
    public String mGetColumnQualifierValue(TableName tableName, String rowKey, String columnFamily, String columnQualifier) {
        String data = "";
        try {
            Table table = connection.getTable(tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnQualifier));
            Result result = table.get(get);
            if (result != null && !result.isEmpty()) {
                Cell cell = result.listCells().get(0);
                data = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            }
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 获取表名
     *
     * @return TableName
     */
    public TableName getTableName(Class<T> clazz) throws HbaseAnnotationException, InstantiationException {
        return this.getHClazzMapping(clazz).getTableName();
    }

    /**
     * 获取表名
     *
     * @return TableName
     */
    public TableName getTableName(String tableName) {
        return TableName.valueOf(tableName);
    }

    /**
     * 获取表映射关系
     *
     * @return TableName
     */
    public HClazzMapping<T> getHClazzMapping(Class<T> clazz) throws HbaseAnnotationException, InstantiationException {
        //获取表名
        com.lylbp.manager.hbase.annotion.HTable hTableAnnotation = clazz.getAnnotation(HTable.class);
        if (null == hTableAnnotation) {
            throw new HbaseAnnotationException(String.format("%s中无法通过HTable注解确认表名", clazz));
        }

        String tableNameStr = hTableAnnotation.tableName();
        if (StrUtil.isEmpty(tableNameStr)) {
            throw new HbaseAnnotationException(String.format("%s中无法通过HTable注解确认表名", clazz));
        }

        HClazzMapping<T> hClazzMapping = new HClazzMapping<>();
        hClazzMapping.setTableName(TableName.valueOf(tableNameStr));
        hClazzMapping.setRowHandler(defaultHandler.getRowHandler(clazz));

        return hClazzMapping;
    }


    /**
     * 获取总数
     *
     * @param tableName 表名
     * @return long
     */
    public long enableAggregation(TableName tableName) throws Throwable {
        return enableAggregation(tableName, null);
    }

    /**
     * 获取总数
     * <property>
     * <name>hbase.coprocessor.user.region.classes</name>
     * <value>org.apache.hadoop.hbase.coprocessor.AggregateImplementation</value>
     * </property>
     *
     * @param tableName  表名
     * @param filterList 过滤器集合
     * @return long
     */
    public long enableAggregation(TableName tableName, FilterList filterList) throws Throwable {
        long rowCount;
        Scan scan = getScan(filterList);

        AggregationClient aggregationClient = new AggregationClient(connection.getConfiguration());
        rowCount = aggregationClient.rowCount(tableName, new LongColumnInterpreter(), scan);
        return rowCount;
    }

    /**
     * 获取最后一行rowKey
     *
     * @param table      表
     * @param page       表
     * @param filterList 过滤器集合
     */
    public byte[] getLastRowKey(Table table, HPage<?> page, FilterList filterList)
            throws IOException {
        int pageNum = (int) page.getCurrent();
        int pageSize = (int) page.getSize();
        byte[] row = new byte[0];

        List<Filter> filters = filterList.getFilters();
        filterList = new FilterList();
        filterList.addFilter(filters);
        if (pageNum == 1) {
            return row;
        }

        //获取lastRowKey
        int pageEnd = (pageNum - 1) * pageSize;
        filterList.addFilter(new PageFilter(pageEnd));
        Scan scan = getScan(filterList);
        ResultScanner start = table.getScanner(scan);
        start.next(pageEnd - 1);

        //封装结果集
        for (Result result : start) {
            row = result.getRow();
        }

        return row;

    }

    /**
     * 获取Scan
     *
     * @param filterList 过滤器集合
     * @return Scan
     */
    public static Scan getScan(FilterList filterList) {
        return new Scan().setFilter(filterList);
    }


}
