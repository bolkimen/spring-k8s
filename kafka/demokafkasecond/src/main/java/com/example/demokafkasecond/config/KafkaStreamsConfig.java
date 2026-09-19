package com.example.demokafkasecond.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.streams.KafkaStreamsInteractiveQueryService;

@Configuration
public class KafkaStreamsConfig {
    @Bean
    public KafkaStreamsInteractiveQueryService
    kafkaStreamsInteractiveQueryService(
            StreamsBuilderFactoryBean streamsBuilderFactoryBean) {

        return new KafkaStreamsInteractiveQueryService(
                streamsBuilderFactoryBean
        );
    }
}
