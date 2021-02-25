//package com.lylbp.manager.hbase.service;
//
//import HBaseUtil;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.hadoop.hbase.Cell;
//import org.apache.hadoop.hbase.CompareOperator;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.filter.*;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.*;
//
///**
// * HbaseService
// *
// * @author weiwenbin
// * @date 2020/11/2 下午1:34
// */
//@Slf4j
//@Service
//@Data
//public class HBaseService {
//    @Resource
//    private Connection connection;
//
//    @Resource
//    private Admin hbaseAdmin;
//
//    /**
//     * 判断表是否存在
//     *
//     * @param tableName 表名
//     * @return true/false
//     */
//    public boolean isExists(String tableName) {
//        boolean tableExists = false;
//        try {
//            tableExists = hbaseAdmin.tableExists(TableName.valueOf(tableName));
//        } catch (IOException e) {
//            log.info(e.getMessage());
//        }
//        return tableExists;
//    }
//
//    /**
//     * 创建表
//     *
//     * @param tableName    表名
//     * @param columnFamily 列簇
//     * @return true/false
//     */
//    public boolean createTable(String tableName, List<String> columnFamily) throws Exception {
//        return createTable(tableName, columnFamily, null);
//    }
//
//    /**
//     * 预分区创建表
//     *
//     * @param tableName    表名
//     * @param columnFamily 列簇
//     * @param keys         分区集合
//     * @return true/false
//     */
//    public boolean createTable(String tableName, List<String> columnFamily, List<String> keys) throws Exception {
//        if (isExists(tableName)) {
//            throw new Exception("表名已存在");
//        }
//
//        try {
//            //表描述器构造器
//            TableDescriptorBuilder tdb = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName));
//            for (String cf : columnFamily) {
//                //列簇描述器构造器
//                ColumnFamilyDescriptorBuilder cdb = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf));
//                //获得列描述器
//                ColumnFamilyDescriptor cfd = cdb.build();
//                //添加列簇
//                tdb.setColumnFamily(cfd);
//            }
//            //获得表描述器
//            TableDescriptor td = tdb.build();
//            //创建表
//            if (keys == null) {
//                hbaseAdmin.createTable(td);
//            } else {
//                byte[][] splitKeys = HBaseUtil.getSplitKeys(keys);
//                hbaseAdmin.createTable(td, splitKeys);
//            }
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    /**
//     * 删除表
//     *
//     * @param tableName 表名
//     */
//    public Boolean dropTable(String tableName) throws Exception {
//        if (!isExists(tableName)) {
//            throw new Exception("表不已存在");
//        }
//        TableName table = TableName.valueOf(tableName);
//        hbaseAdmin.disableTable(table);
//        hbaseAdmin.deleteTable(table);
//
//        return true;
//    }
//
//    /**
//     * 插入单条-单列簇-多列数据数据
//     *
//     * @param tableName    表名
//     * @param rowKey       rowKey
//     * @param columnFamily 列簇
//     * @param column       列
//     * @param value        值
//     * @return true/false
//     */
//    public boolean putData(String tableName, String rowKey, String columnFamily, String column, String value) {
//        ArrayList<String> columns = new ArrayList<>();
//        columns.add(column);
//
//        ArrayList<String> values = new ArrayList<>();
//        values.add(value);
//
//        return putData(tableName, rowKey, columnFamily, columns, values);
//    }
//
//    /**
//     * 插入多条-单列簇-多列数据
//     *
//     * @param tableName    表名
//     * @param rowKey       rowKey
//     * @param columnFamily 列簇
//     * @param columns      列
//     * @param values       值
//     * @return true/false
//     */
//    public boolean putData(String tableName, String rowKey, String columnFamily, List<String> columns,
//                           List<String> values) {
//        try {
//            Table table = hbaseAdmin.getConnection().getTable(TableName.valueOf(tableName));
//            Put put = new Put(Bytes.toBytes(rowKey));
//            for (int i = 0; i < columns.size(); i++) {
//                String column = columns.get(i);
//                String value = values.get(i);
//                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
//            }
//            table.put(put);
//            table.close();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 获取全表数据
//     *
//     * @param tableName     表名
//     */
//    public List<Map<String, String>> getData(String tableName) throws IOException {
//        return getData(tableName, null, null);
//    }
//
//    /**
//     * 获取全表数据
//     *
//     * @param tableName     表名
//     * @param filterRowKeys rowKey过滤器 str$ 末尾匹配，相当于sql中的 %str  ^str开头匹配，相当于sql中的str%
//     * @param filterList    过滤器链[LESS:< ,LESS_OR_EQUAL:<=,EQUAL:=,NOT_EQUAL:<>,GREATER_OR_EQUAL:>=,GREATER:>,NO_OP 排除所有]
//     */
//    public List<Map<String, String>> getData(String tableName, List<String> filterRowKeys, FilterList filterList) throws IOException {
//        //过滤器链为空则自建
//        if (null == filterList) {
//            filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
//        }
//        //rowKey过滤器
//        for (String filterRowKey : filterRowKeys) {
//            //str$ 末尾匹配，相当于sql中的 %str  ^str开头匹配，相当于sql中的str%
//            RowFilter filter = new RowFilter(CompareOperator.EQUAL, new RegexStringComparator(filterRowKey));
//            filterList.addFilter(filter);
//        }
//
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        Scan scan = new Scan();
//        scan.setFilter(filterList);
//
//        ResultScanner resultScanner = table.getScanner(scan);
//        List<Map<String, String>> list = HBaseUtil.resultScanner2MapList(resultScanner);
//        table.close();
//
//        return list;
//    }
//
//    /**
//     * 根据rowKey获取数据
//     *
//     * @param tableName 表名
//     * @param rowKey    rowKey
//     * @return map
//     */
//    public Map<String, String> getData(String tableName, String rowKey) throws IOException {
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        Get get = new Get(Bytes.toBytes(rowKey));
//        Result result = table.get(get);
//        Map<String, String> map = HBaseUtil.result2Map(result);
//        table.close();
//        return map;
//    }
//
//    /**
//     * 根据rowKey、列簇、列获取数据
//     *
//     * @param tableName       表名
//     * @param rowKey          rowKey
//     * @param columnFamily    列簇
//     * @param columnQualifier 列
//     * @return map
//     */
//    public String getData(String tableName, String rowKey, String columnFamily, String columnQualifier) {
//        String data = "";
//        try {
//            Table table = connection.getTable(TableName.valueOf(tableName));
//            Get get = new Get(Bytes.toBytes(rowKey));
//            get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnQualifier));
//            Result result = table.get(get);
//            if (result != null && !result.isEmpty()) {
//                Cell cell = result.listCells().get(0);
//                data = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
//            }
//            table.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
//
//
//    /**
//     * 删除数据（根据rowkey）
//     *
//     * @param tableName 表名
//     * @param rowKey    rowKey
//     */
//    public void deleteData(String tableName, String rowKey) throws IOException {
//        Table table = hbaseAdmin.getConnection().getTable(TableName.valueOf(tableName));
//        Delete delete = new Delete(Bytes.toBytes(rowKey));
//        table.delete(delete);
//        table.close();
//    }
//
//    /**
//     * 删除数据（根据rowkey，列簇）
//     *
//     * @param tableName    表名
//     * @param rowKey       rowKey
//     * @param columnFamily 列簇
//     */
//    public void deleteData(String tableName, String rowKey, String columnFamily)
//            throws IOException {
//        Table table = hbaseAdmin.getConnection().getTable(TableName.valueOf(tableName));
//        Delete delete = new Delete(Bytes.toBytes(rowKey));
//        delete.addFamily(columnFamily.getBytes());
//        table.delete(delete);
//        table.close();
//    }
//
//    /**
//     * 删除数据（根据rowkey，列簇）
//     *
//     * @param tableName    表名
//     * @param rowKey       rowKey
//     * @param columnFamily 列簇
//     * @param column       列
//     */
//    public void deleteData(String tableName, String rowKey, String columnFamily, String column)
//            throws IOException {
//        Table table = hbaseAdmin.getConnection().getTable(TableName.valueOf(tableName));
//        Delete delete = new Delete(Bytes.toBytes(rowKey));
//        delete.addColumn(columnFamily.getBytes(), column.getBytes());
//        table.delete(delete);
//        table.close();
//    }
//
//    /**
//     * 删除数据（多行）
//     *
//     * @param tableName 表名
//     * @param rowKeys   rowKey集合
//     */
//    public void deleteData(String tableName, List<String> rowKeys) throws IOException {
//        Table table = hbaseAdmin.getConnection().getTable(TableName.valueOf(tableName));
//        List<Delete> deleteList = new ArrayList<>();
//        for (String row : rowKeys) {
//            Delete delete = new Delete(Bytes.toBytes(row));
//            deleteList.add(delete);
//        }
//        table.delete(deleteList);
//        table.close();
//    }
//}
