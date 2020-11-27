package com.lylbp.manger.hbase.handler;

import cn.hutool.core.util.StrUtil;
import com.lylbp.manger.hbase.converter.ConversionService;
import com.lylbp.manger.hbase.handler.exception.HbaseAnnotationException;
import com.lylbp.manger.hbase.annotion.HColumn;
import com.lylbp.manger.hbase.annotion.HIgnore;
import com.lylbp.manger.hbase.annotion.HTable;
import com.lylbp.manger.hbase.annotion.HRowKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 字段映射关系
 *
 * @Author weiwenbin
 * @Date 2020/11/4 上午11:14
 */
@Slf4j
@Data
public class FieldMapper<T> {

    /**
     * 转换器
     */
    private ConversionService conversionService;

    /**
     * 所属javaBean类型
     */
    private Class<?> beanClazz;

    /**
     * 字段的类型
     */
    private Class<?> filedClazz;

    /**
     * 是否是rowKey
     */
    private boolean isRowKey;

    /**
     * 是否是普通字段
     */
    private boolean isColumn;

    /**
     * 是否忽略
     */
    private boolean isIgnore;

    /**
     * get方法
     */
    private Method getMethod;

    /**
     * set方法
     */
    private Method setMethod;

    /**
     * 列簇
     */
    private byte[] family;

    /**
     * 列
     */
    private byte[] qualifier;

    /**
     * 类的HTable注解
     */
    private HTable hTableAnnotation;

    /**
     * 字段的HRowkey注解
     */
    private HRowKey hRowKeyAnnotation;

    /**
     * 字段的HColumn注解
     */
    private HColumn hColumnAnnotation;

    /**
     * 字段的HIgnore注解
     */
    private HIgnore hIgnoreAnnotation;

    private void checkAnnotation(final Class<T> clazz, final Field field) throws HbaseAnnotationException {
        //1.类注解验证
        //类上必须含有HRow注解
        if (!clazz.isAnnotationPresent(HTable.class)) {
            throw new HbaseAnnotationException(String.format("%s没有HTable注解", clazz));
        }


        //2.字段注解验证
        //HIgnore、HRowKey、HColumn必须写一个
        if (null == hIgnoreAnnotation && null == hRowKeyAnnotation && null == hColumnAnnotation) {
            throw new HbaseAnnotationException(
                    String.format("HIgnore、HRowKey、HColumn必须写一个(类:%s,字段:%s)", clazz.getName(), field.getName())
            );
        }

        //HRowKey与HColumn不能同时使用
        if (null != hRowKeyAnnotation && null != hColumnAnnotation) {
            throw new HbaseAnnotationException(
                    String.format("HRowKey与HColumn不能同时使用(类:%s,字段:%s)", clazz.getName(), field.getName())
            );
        }

        //HIgnore只能单独使用
        boolean isOnlyIgnore = (null != hIgnoreAnnotation) && (null != hRowKeyAnnotation || null != hColumnAnnotation);
        if (isOnlyIgnore) {
            throw new HbaseAnnotationException(
                    String.format("HIgnore只能单独使用(类:%s,字段:%s)", clazz.getName(), field.getName())
            );
        }
    }

    public FieldMapper(final Class<T> clazz, final Field field, final ConversionService conversionService)
            throws InstantiationException {
        try {
            this.hTableAnnotation = clazz.getAnnotation(HTable.class);
            this.hRowKeyAnnotation = field.getAnnotation(HRowKey.class);
            this.hColumnAnnotation = field.getAnnotation(HColumn.class);
            this.hIgnoreAnnotation = field.getAnnotation(HIgnore.class);

            //检测注解
            checkAnnotation(clazz, field);

            this.conversionService = conversionService;

            this.beanClazz = clazz;
            this.filedClazz = field.getType();

            this.getMethod = this.buildGetMethod(field);
            this.setMethod = this.buildSetMethod(field);

            //若为忽略则代表当前字段在hbase中不存在故既不是rowKey与不是普通列簇的列
            this.isIgnore = hIgnoreAnnotation != null;
            if (this.isIgnore) {
                this.isRowKey = false;
                this.isColumn = false;
            } else {
                this.isRowKey = hRowKeyAnnotation != null;
                this.isColumn = !this.isRowKey && hColumnAnnotation != null;
            }

            if (isColumn) {
                this.family = this.getFamily(field);
                this.qualifier = this.getQualifier(field);
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new InstantiationException(ex.getMessage());
        }
    }

    /**
     * javabean转Put
     * 将javabean对应的字段值加进Put
     *
     * @param put Put
     * @param t   javabean
     */
    public void addColumnToPut(final Put put, final T t) throws ReflectiveOperationException {
        Object value = this.getMethod.invoke(t);
        if (value != null && this.isColumn) {
            put.addColumn(this.family, this.qualifier, this.conversionService.convert(value));
        }
    }

    /**
     * 将对应结果集的列赋值给对应javabean字段
     *
     * @param t      要转的javabean
     * @param result 结果集
     */
    public void evalToTarget(final T t, final Result result) throws ReflectiveOperationException {
        if (null != result) {
            byte[] value;
            if (this.isRowKey) {
                value = result.getRow();
            } else {
                value = result.getValue(this.family, this.qualifier);
            }
            if (value != null) {
                this.setMethod.invoke(t, this.conversionService.from(value, this.filedClazz));
            }
        }
    }

    /**
     * 获取列簇
     *
     * @param field javaBean对应的字段
     * @return byte[]
     */
    public byte[] getFamily(final Field field) throws HbaseAnnotationException {
        String family = hColumnAnnotation.family();
        //列簇为空取默认
        if (StrUtil.isEmpty(family)) {
            family = hTableAnnotation.defaultFamily();
        }

        if (StrUtil.isEmpty(family)) {
            throw new HbaseAnnotationException(
                    String.format("必须在HColumn填写列簇或在HTable中填写defaultFamily(类:%s,字段:%s)",
                            this.beanClazz.getName(), field.getName())
            );
        }

        return Bytes.toBytes(family);
    }

    /**
     * 获取列
     *
     * @param field javaBean对应的字段
     * @return byte[]
     */
    public byte[] getQualifier(final Field field) {
        String qualifier = hColumnAnnotation.qualifier();
        //qualifier为空默认为字段名称
        if (StrUtil.isEmpty(qualifier)) {
            return Bytes.toBytes(field.getName());
        }

        return Bytes.toBytes(qualifier);
    }

    /**
     * 获取get方法
     *
     * @param field javaBean对应的字段
     * @return Method
     */
    public Method buildGetMethod(final Field field) throws NoSuchMethodException {
        //字段名称
        String name = field.getName();
        //字段是否是布尔类型
        boolean fileTypeIsBoolean = Boolean.class == filedClazz || boolean.class == filedClazz;

        if (fileTypeIsBoolean) {
            name = name.replaceAll("^is", "");
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        String methodName = "get" + name;
        if (fileTypeIsBoolean) {
            methodName = "is" + name;
        }
        return this.beanClazz.getDeclaredMethod(methodName);
    }

    /**
     * 获取set方法
     *
     * @param field javaBean对应的字段
     * @return Method
     */
    public Method buildSetMethod(final Field field) throws NoSuchMethodException {
        String name = field.getName();
        if (Boolean.class == field.getType() || boolean.class == field.getType()) {
            name = name.replaceAll("^is", "");
        }
        String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        return this.beanClazz.getDeclaredMethod(methodName, field.getType());
    }
}
