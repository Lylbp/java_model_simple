package com.lylbp.manager.kafka.demo.controller;

import com.lylbp.common.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.manager.kafka.service.KafkaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;


/**
 * TestKafkaController
 *
 * @author alex
 */
@RestController
@RequestMapping("/test/kafka")
@Api(tags = "测试kafka")
@Slf4j
@ConditionalOnProperty(prefix = "kafka", name = "bootstrap-servers")
public class TestKafkaController {
    @Resource
    private KafkaService kafkaService;

    @PostMapping("/send")
    @ApiOperation(value = "发送信息")
    public ResResult<Boolean> send(@RequestParam String msg, @RequestParam String topic) {
        kafkaService.send(msg, topic);

        return ResResultUtil.success(true);
    }

    /**
     * 测试发送完后监听
     *
     * @param record record
     * @param ack    ack
     * @param topic  topic
     */
    @KafkaListener(topics = "test", groupId = "test")
    public void topic_test(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
            ack.acknowledge();
        }
    }
}
