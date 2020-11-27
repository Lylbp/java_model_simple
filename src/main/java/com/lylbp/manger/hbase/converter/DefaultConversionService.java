package com.lylbp.manger.hbase.converter;

import cn.hutool.core.date.DateUtil;
import org.apache.hadoop.hbase.util.Bytes;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * 默认的转换服务
 *
 * @Author weiwenbin
 * @Date 2020/11/3 下午3:43
 */
public class DefaultConversionService extends GenericConversionService {
    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    /**
     * 添加默认转换器
     *
     * @param converterRegistry 转换注册器
     */
    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverter(new Converter<Integer>() {
            @Override
            public byte[] convert(final Integer source) {
                return Bytes.toBytes(source);
            }

            @Override
            public Integer from(final byte[] bytes) {
                return Bytes.toInt(bytes);
            }
        });

        converterRegistry.addConverter(new Converter<String>() {
            @Override
            public byte[] convert(final String source) {
                return Bytes.toBytes(source);
            }

            @Override
            public String from(final byte[] bytes) {
                return Bytes.toString(bytes);
            }
        });

        converterRegistry.addConverter(new Converter<Long>() {
            @Override
            public byte[] convert(final Long source) {
                return Bytes.toBytes(source);
            }

            @Override
            public Long from(final byte[] bytes) {
                return Bytes.toLong(bytes);
            }

        });

        converterRegistry.addConverter(new Converter<Float>() {
            @Override
            public byte[] convert(final Float source) {
                return Bytes.toBytes(source);
            }

            @Override
            public Float from(final byte[] bytes) {
                return Bytes.toFloat(bytes);
            }

        });

        converterRegistry.addConverter(new Converter<Double>() {
            @Override
            public byte[] convert(final Double source) {
                return Bytes.toBytes(source);
            }

            @Override
            public Double from(final byte[] bytes) {
                return Bytes.toDouble(bytes);
            }

        });

        converterRegistry.addConverter(new Converter<Boolean>() {
            @Override
            public byte[] convert(final Boolean source) {
                return Bytes.toBytes(source);
            }

            @Override
            public Boolean from(final byte[] bytes) {
                return Bytes.toBoolean(bytes);
            }
        });

        converterRegistry.addConverter(new Converter<Short>() {
            @Override
            public byte[] convert(final Short source) {
                return Bytes.toBytes(source);
            }

            @Override
            public Short from(final byte[] bytes) {
                return Bytes.toShort(bytes);
            }
        });

        converterRegistry.addConverter(new Converter<BigDecimal>() {
            @Override
            public byte[] convert(final BigDecimal source) {
                return Bytes.toBytes(source);
            }

            @Override
            public BigDecimal from(final byte[] bytes) {
                return Bytes.toBigDecimal(bytes);
            }
        });

        converterRegistry.addConverter(new Converter<ByteBuffer>() {
            @Override
            public byte[] convert(final ByteBuffer source) {
                return Bytes.toBytes(source);
            }

            @Override
            public ByteBuffer from(final byte[] bytes) {
                return ByteBuffer.wrap(bytes);
            }
        });

        converterRegistry.addConverter(new Converter<Date>() {
            @Override
            public byte[] convert(final Date source) {
                return Bytes.toBytes(source.toString());
            }

            @Override
            public Date from(final byte[] bytes) {
                return DateUtil.parse(Bytes.toString(bytes));
            }
        });
    }


}
