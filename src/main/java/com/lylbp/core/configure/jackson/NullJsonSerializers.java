package com.lylbp.core.configure.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Description
 * @Author weiwenbin
 * @Date 2024/10/14 9:45
 */
public interface NullJsonSerializers {
    JsonSerializer<Object> ARRAY_JSON_SERIALIZER = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        }
    };

    JsonSerializer<Object> NUMBER_JSON_SERIALIZER = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(-1);
        }
    };


    JsonSerializer<Object> BOOLEAN_JSON_SERIALIZER = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeBoolean(false);
        }
    };


    JsonSerializer<Object> STRING_JSON_SERIALIZER = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString("");
        }
    };


    JsonSerializer<Object> OBJECT_JSON_SERIALIZER = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeEndObject();
        }
    };
}
