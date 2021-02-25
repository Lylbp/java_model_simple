package com.lylbp.manager.hbase.converter;

/**
 * 转换器
 *
 * @author weiwenbin
 * @date 2020/11/3 下午3:35
 */
public interface Converter<T> {
    /**
     * javaBean转字节数组
     *
     * @param t javaBean
     * @return byte[]
     */
    byte[] convert(T t);

    /**
     * 字节数组转javaBean
     *
     * @param bytes
     * @return T
     */
    T from(byte[] bytes);
}
