package com.lylbp.manager.kafka.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "spring.kafka", name = "enable", havingValue = "true" )
public class KafkaConfiguration {
//    @Bean
//    public NewTopic gpsTopic() {
//        return new NewTopic("test", 5, (short) 3);
//    }
}
