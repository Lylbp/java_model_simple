package com.lylbp.manger.hbase.handler;

import com.lylbp.manger.hbase.converter.ConversionService;
import com.lylbp.manger.hbase.converter.DefaultConversionService;
import com.lylbp.manger.hbase.handler.exception.HandlerException;
import lombok.Data;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的处理器
 *
 * @Author weiwenbin
 * @Date 2020/11/4 上午9:28
 */
@Data
public class DefaultHandler<T> implements HandlerInterface<T> {
    /**
     * 转换器服务
     */
    private ConversionService conversionService;

    /**
     * 转换器缓存
     */
    private Map<Class<T>, HRowHandler<T>> handlerCaches;

    public DefaultHandler() {
        this.conversionService = new DefaultConversionService();
        handlerCaches = new HashMap<>();
    }

    public DefaultHandler(ConversionService conversionService) {
        this();
        this.conversionService = conversionService;
    }

    /**
     * 结果集转javaBean
     *
     * @param result 结果集
     * @param clazz  要转成的class
     * @return T
     * @throws HandlerException 处理异常
     */
    @Override
    public T convert(Result result, Class<T> clazz, HRowHandler<T> hRowHandler) throws HandlerException {
        try {
            return hRowHandler.convert(result);
        } catch (Exception ex) {
            throw new HandlerException(ex);
        }
    }

    /**
     * javaBean转Put
     *
     * @param t javaBean
     * @return Put
     * @throws HandlerException 处理异常
     */
    @Override
    public Put buildPut(T t, HRowHandler<T> hRowHandler) throws HandlerException {
        try {
            return hRowHandler.buildPut(t);
        } catch (Exception ex) {
            throw new HandlerException(ex);
        }
    }


    /**
     * 获取HRowHandler对象
     *
     * @param clazz javaBean的class
     * @return HRowHandler<T>
     * @throws InstantiationException 初始化异常
     */
    public HRowHandler<T> getRowHandler(Class<T> clazz) throws InstantiationException {
        if (!handlerCaches.containsKey(clazz)) {
            synchronized (this) {
                if (!handlerCaches.containsKey(clazz)) {
                    handlerCaches.put(clazz, new HRowHandler<>(clazz, conversionService));
                }
            }
        }
        return handlerCaches.get(clazz);
    }
}
