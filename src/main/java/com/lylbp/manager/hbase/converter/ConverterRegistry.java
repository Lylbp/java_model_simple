package com.lylbp.manager.hbase.converter;

/**
 * 转换器注册
 *
 * @author weiwenbin
 * @date 2020/11/3 下午3:38
 */
public interface ConverterRegistry {
    /**
     * 添加转换器
     *
     * @param converter 转换器
     */
    void addConverter(Converter<?> converter);
}
