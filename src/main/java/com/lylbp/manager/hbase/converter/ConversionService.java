package com.lylbp.manager.hbase.converter;

/**
 * 转换服务接口
 *
 * @author weiwenbin
 * @date 2020/11/3 下午3:41
 */
public interface ConversionService {
    /**
     * javaBean转byte数组
     *
     * @param t   javaBean
     * @param <T> 泛型
     * @return byte[]
     */
    <T> byte[] convert(T t);

    /**
     * byte数组转javaBean
     *
     * @param bytes byte数组
     * @param type  要转的javaBean的class
     * @param <T>   泛型
     * @return T
     */
    <T> T from(byte[] bytes, Class<T> type);
}
