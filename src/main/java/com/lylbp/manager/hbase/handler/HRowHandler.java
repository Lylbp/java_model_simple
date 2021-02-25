package com.lylbp.manager.hbase.handler;

import com.lylbp.manager.hbase.converter.ConversionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author weiwenbin
 */
@Slf4j
@Data
public class HRowHandler<T> {
    /**
     * 要转的javaBean的class
     */
    private Class<T> beanClazz;

    /**
     * rowKey字段映射
     */
    private FieldMapper<T> rowKeyMapper;

    /**
     * 列字段映射
     */
    private List<FieldMapper<T>> columnMappers;

    /**
     * 转换器
     */
    private ConversionService conversionService;

    public HRowHandler(final Class<T> clazz, final ConversionService conversionService) throws InstantiationException {
        try {
            this.beanClazz = clazz;
            this.conversionService = conversionService;
            this.buildMappers();
        } catch (Exception ex) {
            throw new InstantiationException(ex.getMessage());
        }
    }

    /**
     * javaBean转Put
     *
     * @param t javaBean
     * @return Put
     */
    public Put buildPut(final T t) throws ReflectiveOperationException {
        Put put = new Put(conversionService.convert(this.rowKeyMapper.getGetMethod().invoke(t)));
        for (FieldMapper<T> columnFieldMapper : this.columnMappers) {
            columnFieldMapper.addColumnToPut(put, t);
        }

        return put;
    }

    /**
     * 结果集转javaBean
     *
     * @param result 结果集
     * @return T
     */
    public T convert(final Result result) throws ReflectiveOperationException {
        T t = this.beanClazz.newInstance();
        this.rowKeyMapper.evalToTarget(t, result);
        for (FieldMapper<T> mapper : this.columnMappers) {
            mapper.evalToTarget(t, result);
        }

        return t;
    }

    /**
     * 获取当前javaBean中所有的字段映射
     */
    private void buildMappers() throws InstantiationException {
        //rowKey字段映射
        List<FieldMapper<T>> cms = new LinkedList<>();
        //列字段映射
        FieldMapper<T> rm = null;

        //获取javaBean中所有的成员变量
        Field[] fields = this.beanClazz.getDeclaredFields();
        for (Field field : fields) {
            FieldMapper<T> mapper = new FieldMapper<>(this.beanClazz, field, this.conversionService);

            //忽略注解不处理
            if (mapper.isIgnore()) {
                continue;
            }

            //是rowKey
            if (mapper.isRowKey()) {
                rm = mapper;
            }

            //是普通列
            if (mapper.isColumn()) {
                cms.add(mapper);
            }
        }

        this.rowKeyMapper = rm;
        this.columnMappers = cms;
    }

}
