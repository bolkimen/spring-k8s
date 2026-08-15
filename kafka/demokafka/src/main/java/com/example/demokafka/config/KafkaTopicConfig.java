package com.example.demokafka.config;

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
    private List<String> topicNames = List.of("123", "321"); // <-- initialize your list of topic names, or read from properties

    @Value(value = "${spring.kafka.topic-name}")
    private String topicName;

    @Bean
    public KafkaAdmin.NewTopics createKafkaTopics() {

        List<NewTopic> topics = new ArrayList<>();

        topicNames.forEach(topic -> topics.add(TopicBuilder.name(topic).partitions(11).replicas(1).build()));

        return new KafkaAdmin.NewTopics(topics.toArray(NewTopic[]::new));
    }

    @Bean
    public NewTopic testTopic() {
        return TopicBuilder.name(topicName)
                .partitions(11)
                .replicas(3)
                .compact()
                .build();
    }
}
