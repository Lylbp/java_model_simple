package com.lylbp.core.configure.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Description 自定义BeanSerializerModifier
 * @Author weiwenbin
 * @Date 2024/10/14 9:43
 */
public class DynamicBeanSerializerModifier extends BeanSerializerModifier {
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        //循环所有的beanPropertyWriter
        for (BeanPropertyWriter beanProperty : beanProperties) {
            // 如果已经有 null 序列化处理如注解：@JsonSerialize跳过
            if (beanProperty.hasNullSerializer()) {
                continue;
            }
            JavaType type = beanProperty.getType();
            Class<?> clazz = type.getRawClass();

            //数据处理
            if (type.isArrayType() || clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
                beanProperty.assignNullSerializer(NullJsonSerializers.ARRAY_JSON_SERIALIZER);
            } else if (Number.class.isAssignableFrom(clazz)) {
                beanProperty.assignNullSerializer(NullJsonSerializers.NUMBER_JSON_SERIALIZER);
            } else if (clazz.equals(Boolean.class)) {
                beanProperty.assignNullSerializer(NullJsonSerializers.BOOLEAN_JSON_SERIALIZER);
            } else if (CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
                beanProperty.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
            } else if (type.isTypeOrSubTypeOf(OffsetDateTime.class) ||
                    type.isTypeOrSubTypeOf(Date.class) || type.isTypeOrSubTypeOf(TemporalAccessor.class)) {
                beanProperty.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
            } else {
                beanProperty.assignNullSerializer(NullJsonSerializers.OBJECT_JSON_SERIALIZER);
            }
        }
        return beanProperties;
    }
}
