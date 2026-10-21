package com.example.demokafkasecond.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseKafkaStreamsController {
    @Autowired
    protected StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    protected KafkaStreams getKafkaStreams() {
        KafkaStreams kafkaStreams =
                streamsBuilderFactoryBean.getKafkaStreams();

        if (kafkaStreams == null) {
            throw new IllegalStateException(
                    "Kafka Streams has not started yet"
            );
        }

        return kafkaStreams;
    }
}
