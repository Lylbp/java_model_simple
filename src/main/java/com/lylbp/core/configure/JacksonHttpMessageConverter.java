package com.lylbp.core.configure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 使用官方自带的json格式类库，fastjson因为content type问题时不时控制台报错、无法直接返回二进制等问题
 *
 * @author weiwenbin
 */
@Configuration
class JacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    JacksonHttpMessageConverter() {
        getObjectMapper().setSerializerFactory(
                getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).
                        getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier())
        );
    }

    public class MyBeanSerializerModifier extends BeanSerializerModifier {
        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            //循环所有的beanPropertyWriter
            for (Object beanProperty : beanProperties) {
                BeanPropertyWriter writer = (BeanPropertyWriter) beanProperty;
                Class<?> clazz = writer.getType().getRawClass();

                //数据处理
                if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
                    writer.assignNullSerializer(new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator,
                                              SerializerProvider serializerProvider) throws IOException {
                            if (o == null) {
                                jsonGenerator.writeStartArray();
                                jsonGenerator.writeEndArray();
                            }
                        }
                    });
                } else if (Number.class.isAssignableFrom(clazz)) {
                    writer.assignNullSerializer(new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator,
                                              SerializerProvider serializerProvider) throws IOException {
                            jsonGenerator.writeString(StringUtils.EMPTY);
                        }
                    });
                } else if (clazz.equals(Boolean.class)) {
                    writer.assignNullSerializer(new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator,
                                              SerializerProvider serializerProvider) throws IOException {
                            jsonGenerator.writeBoolean(false);
                        }
                    });
                } else if (CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
                    writer.assignNullSerializer(new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator,
                                              SerializerProvider serializerProvider) throws IOException {
                            jsonGenerator.writeString(StringUtils.EMPTY);
                        }
                    });
                } else if (Date.class.equals(clazz)) {
                    writer.assignNullSerializer(new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator,
                                              SerializerProvider serializerProvider) throws IOException {
                            jsonGenerator.writeString(StringUtils.EMPTY);
                        }
                    });
                } else {
                    writer.assignNullSerializer(new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                            jsonGenerator.writeStartObject();
                            jsonGenerator.writeEndObject();
                        }
                    });
                }
            }
            return beanProperties;
        }

    }
}
