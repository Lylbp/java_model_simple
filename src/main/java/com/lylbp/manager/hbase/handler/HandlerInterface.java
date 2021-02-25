package com.lylbp.manager.hbase.handler;

import com.lylbp.manager.hbase.handler.exception.HandlerException;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

/**
 * 处理器接口
 *
 * @author weiwenbin
 * @date 2020/11/4 上午9:25
 */
public interface HandlerInterface<T> {
    /**
     * 结果集转javaBean
     *
     * @param result      结果集
     * @param clazz       javaBean的class
     * @param hRowHandler hRowHandler
     * @return T
     */
    T convert(Result result, Class<T> clazz, HRowHandler<T> hRowHandler) throws HandlerException;

    /**
     * avaBean转Put
     *
     * @param t           javaBean
     * @param hRowHandler hRowHandler
     * @return Put
     */
    Put buildPut(T t, HRowHandler<T> hRowHandler) throws HandlerException;
}
