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

    @Value(value = "${spring.kafka.customers-topic-name}")
    private String customerTopicName;

    @Value(value = "${spring.kafka.customers-table-topic-name}")
    private String customerTableTopicName;

    @Bean
    public NewTopic streamingTopic22Topic() {
        return TopicBuilder.name("streamingTopic22")
                .partitions(11)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createInputTopic() {
        return TopicBuilder.name("input-topic")
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic streamingOutputTopic() {
        return TopicBuilder.name("output-topic")
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createCustomerTopicName() {
        return TopicBuilder.name(customerTopicName)
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createCustomerTableTopicName() {
        return TopicBuilder.name(customerTableTopicName)
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }
}
