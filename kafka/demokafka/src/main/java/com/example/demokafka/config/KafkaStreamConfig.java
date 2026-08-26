package com.example.demokafka.config;

import com.example.demokafka.dto.Order;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JacksonJsonSerde;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaStreamConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JacksonJsonSerde.class);

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<String, Order> orders(StreamsBuilder builder) {

        KStream<String, Order> stream = builder.stream("input-order-topic");

        stream
                .peek((key, tx) -> System.out.println("⚠️ orders FRAUD ALERT for {}" + key + " with value " + tx))
                .filter((key, order) -> order.getAmount() > 100)
                .mapValues(order -> {
                    order.setAmount(order.getAmount() * 1.23);
                    return order;
                })
                .to("output-order-topic");

        return stream;
    }

    /*@Bean
    public KStream<String, String> pipeline(StreamsBuilder builder) {
        KStream<String, String> stream = builder.stream("input-order-topic");
        stream
                .peek((key, tx) -> System.out.println("⚠️ FRAUD ALERT for {}" + key + " with value " + tx))
                .flatMapValues(line -> Arrays.asList(line.toLowerCase().split("\\W+")))
                .filter((k, word) -> !word.isEmpty())
                .groupBy((k, word) -> word)
                .count(Materialized.as("word-counts"))
                .toStream()
                .to("output-order-topic", Produced.with(Serdes.String(), Serdes.Long()));
        return stream;
    }*/
}
