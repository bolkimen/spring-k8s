package com.example.demokafkasecond.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic streamingTopic22Topic() {
        return TopicBuilder.name("streamingTopic22")
                .partitions(11)
                .replicas(3)
                .compact()
                .build();
    }
}
