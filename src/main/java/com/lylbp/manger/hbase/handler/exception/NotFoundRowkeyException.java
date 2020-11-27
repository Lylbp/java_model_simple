package com.lylbp.manger.hbase.handler.exception;

/**
 * 未找到rowKey异常
 * @Author weiwenbin
 */
public class NotFoundRowkeyException extends RuntimeException {

    public NotFoundRowkeyException(final String message) {
        super(message);
    }

    public NotFoundRowkeyException(final Class<?> clazz) {
        this("not found rowkey in class " + clazz.getName());
    }

}
