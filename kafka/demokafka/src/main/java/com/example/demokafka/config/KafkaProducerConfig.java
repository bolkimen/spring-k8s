package com.example.demokafka.config;

import com.example.demokafka.dto.Greeting;
import com.example.demokafka.dto.Order;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory producerFactory() {
        var producerConfigs = producerConfigs();
        producerConfigs.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JacksonJsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerConfigs);
    }

    @Bean
    public Map producerConfigs() {
        Map props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // See https://kafka.apache.org/42/documentation/#producerconfigs for more properties
        //props.put(ProducerConfig.ACKS_CONFIG,"0"); // the producer doesn’t wait for a reply from the broker. It assumes that the message is sent successfully
        //props.put(ProducerConfig.ACKS_CONFIG,"1"); // The producer receives a successful response from the broker the moment the leader replica receives the message.
        props.put(ProducerConfig.ACKS_CONFIG,"all"); // The producer receives a success response from the broker once all in-sync replicas receive the message
        return props;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(KafkaProperties kafkaProperties) {
        Map<String, Object> kafkaPropertiesMap = kafkaProperties.buildProducerProperties();
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaPropertiesMap));
    }

    //@Bean
    //public KafkaTemplate kafkaTemplate() {
    //    return new KafkaTemplate(producerFactory());
    //}

    @Bean
    public KafkaTemplate<String, Greeting> greetingKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory orderProducerFactory() {
        Map props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        // See https://kafka.apache.org/42/documentation/#producerconfigs for more properties
        //props.put(ProducerConfig.ACKS_CONFIG,"0"); // the producer doesn’t wait for a reply from the broker. It assumes that the message is sent successfully
        //props.put(ProducerConfig.ACKS_CONFIG,"1"); // The producer receives a successful response from the broker the moment the leader replica receives the message.
        props.put(ProducerConfig.ACKS_CONFIG,"all"); // The producer receives a success response from the broker once all in-sync replicas receive the message

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Order> kafkaOrderStreamTemplate(
            ProducerFactory<String, Order> orderProducerFactory) {
        return new KafkaTemplate<>(orderProducerFactory);
    }
}
