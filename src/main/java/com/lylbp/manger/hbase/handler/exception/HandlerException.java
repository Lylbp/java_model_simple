package com.lylbp.manger.hbase.handler.exception;


/**
 * 转换异常
 *
 * @author weiwenbin
 */
public class HandlerException extends Exception {
    public HandlerException(final Exception ex) {
        super(ex);
    }

}
