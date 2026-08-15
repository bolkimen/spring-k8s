package com.example.demokafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BackgroundProcessor {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Scheduled(fixedRate = 600)
    public void cleanOldFiles() {
        System.out.println("Running file cleanup...");
        // logic here
        kafkaTemplate.send("test-topic", "Hello from BackgroundProcessor!");
    }
}
