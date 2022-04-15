package com.lylbp.manager.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * KafkaService
 *
 * @Author weiwenbin
 * @Date 2020/7/16 下午5:19
 */
@Component
@Slf4j
@ConditionalOnProperty(prefix = "spring.kafka", name = "enable", havingValue = "true" )
public class KafkaService {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String jsonMsg, String topic) {
        //发送消息
        kafkaTemplate.send(topic, jsonMsg).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.error(topic + " - 生产者 发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                RecordMetadata recordMetadata = stringObjectSendResult.getRecordMetadata();
                //成功的处理
                log.debug(topic + " - 生产者 发送消息成功：" + " partition:" + recordMetadata.partition()
                        + " offset:" + recordMetadata.offset());
            }
        });
    }
}
