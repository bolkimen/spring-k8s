package com.example.demokafkasecond.service;

import com.example.demokafkasecond.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BackgroundProcessor {

    @Value(value = "${spring.kafka.customers-topic-name}")
    private String customerTopicName;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaTemplate customerKafkaTemplate;

    @Scheduled(fixedRate = 1200)
    public void cleanOldFiles() {
        System.out.println("Running file cleanup...");
        // logic here
        kafkaTemplate.send("streamingTopic21", UUID.randomUUID().toString(), "Hello from BackgroundProcessor!");

        kafkaTemplate.send("input-topic", UUID.randomUUID().toString(), "Hello word " + UUID.randomUUID().toString());

        customerKafkaTemplate.send(
                customerTopicName,
                "123",
                new Customer("123", "John", "john@lalalala.com")
        );
    }
}
