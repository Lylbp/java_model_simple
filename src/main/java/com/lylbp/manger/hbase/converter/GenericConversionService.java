package com.lylbp.manger.hbase.converter;

import com.lylbp.manger.hbase.converter.exception.NotFoundConverterException;
import lombok.Data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 一般的转换服务
 *
 * @Author weiwenbin
 * @Date 2020/11/3 下午3:43
 */
@Data
public class GenericConversionService implements ConversionService, ConverterRegistry {
    /**
     * 转换器集合
     */
    private List<Converter<?>> converters = new LinkedList<>();

    /**
     * 转换器缓存
     */
    private Map<Class<?>, Converter<?>> converterCache = new HashMap<>();

    /**
     * javaBean转byte数组
     *
     * @param t   javaBean
     * @param <T> 泛型
     * @return byte[]
     */
    @Override
    public <T> byte[] convert(T t) {
        Converter<T> converter = (Converter<T>) getConverter(t.getClass());
        return converter.convert(t);
    }

    /**
     * byte数组转javaBean
     *
     * @param bytes byte数组
     * @param type  要转的javaBean的class
     * @param <T>   泛型
     * @return T
     */
    @Override
    public <T> T from(byte[] bytes, Class<T> type) {
        Converter<T> converter = (Converter<T>) getConverter(type);
        return converter.from(bytes);
    }

    /**
     * 添加转换器
     *
     * @param converter 转换器
     */
    @Override
    public void addConverter(Converter<?> converter) {
        this.converters.add(converter);
    }

    /**
     * 通过class获取转换器,若不存在则加入缓存,每次从缓存中取转换器
     *
     * @param clazz 要转换的类型
     * @return Converter
     */
    public Converter<?> getConverter(Class<?> clazz) {
        //缓存中是否含有当前类型转换器
        boolean cacheHasConverter = converterCache.containsKey(clazz);
        if (!cacheHasConverter) {
            converterCache.put(clazz, ensureConverterByClass(clazz));
        }

        Converter<?> converter = converterCache.get(clazz);
        if (null == converter) {
            throw new NotFoundConverterException(clazz);
        }
        return converter;
    }

    /**
     * 通过class确认转换器类型
     *
     * @param clazz 要转的类型
     * @return Converter
     */
    public Converter<?> ensureConverterByClass(Class<?> clazz) {
        //遍历默认转换器,并通过反射与泛型获取到对应转换器所能转换的类型,从而匹配到转换器
        for (Converter<?> converter : converters) {
            Class<?> converterClazz = converter.getClass();
            //通过反射泛型获取转换器被限制的泛型类型
            Type[] types = converterClazz.getGenericInterfaces();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType pType = (ParameterizedType) type;
                    Class<?> rawTypeClass = (Class<?>) pType.getRawType();
                    if (!rawTypeClass.isAssignableFrom(Converter.class)) {
                        continue;
                    }

                    Type[] actualTypeArguments = pType.getActualTypeArguments();
                    Class<?> converterClass = (Class<?>) actualTypeArguments[0];
                    if (converterClass.isAssignableFrom(clazz)) {
                        return converter;
                    }
                }
            }
        }

        return null;
    }

}
