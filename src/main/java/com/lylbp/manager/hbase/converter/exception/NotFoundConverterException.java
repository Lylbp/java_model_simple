package com.lylbp.manager.hbase.converter.exception;

/**
 * 未找到装换服务异常
 *
 * @author weiwenbin
 */
public class NotFoundConverterException extends RuntimeException {

    public NotFoundConverterException(final String message) {
        super(message);
    }

    public NotFoundConverterException(final Class<?> clazz) {
        this(String.format("错误,未找到与%s对应的转换器", clazz.getName()));
    }

}
