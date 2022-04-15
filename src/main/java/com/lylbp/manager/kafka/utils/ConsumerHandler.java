package com.lylbp.manager.kafka.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ConsumerHandler {

    // 本例中使用一个consumer将消息放入后端队列，你当然可以使用前一种方法中的多实例按照某张规则同时把消息放入后端队列
    private KafkaConsumer<String, String> consumer;
    private ExecutorService executors;
    private Properties props;

    public ConsumerHandler(KafkaProperties kafkaProperties, String groupId, String topic) {
        List<String> bootstrapServers = kafkaProperties.getBootstrapServers();
        String brokerList = bootstrapServers.stream().map(String::valueOf).collect(Collectors.joining(","));

        props = new Properties();
        props.put("bootstrap.servers", brokerList);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void execute(int workerNum, Class<?> cls, String topic) throws Exception {
        executors = new ThreadPoolExecutor(workerNum, workerNum, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
        Constructor<?> constructor = cls.getConstructor(ConsumerRecord.class);
        new Thread(()->{
            while (true) {
                //kafka为空重连
                if (consumer != null) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));

                    for (final ConsumerRecord record : records) {
                        Runnable object = null;
                        try {
                            object = (Runnable) constructor.newInstance(record);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        executors.submit(object);
                    }
                } else {
                    consumer = new KafkaConsumer<>(props);
                    consumer.subscribe(Collections.singletonList(topic));
                }
            }
        }).start();
    }

    public void stop() {
        if (consumer != null) {
            consumer.wakeup();
        }
    }

    public void shutdown() {
        if (consumer != null) {
            consumer.close();
        }
        if (executors != null) {
            executors.shutdown();
        }
        try {
            if (!executors.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Timeout.... Ignore for this case");
            }
        } catch (InterruptedException ignored) {
            System.out.println("Other thread interrupted this shutdown, ignore for this case.");
            Thread.currentThread().interrupt();
        }
    }

}
