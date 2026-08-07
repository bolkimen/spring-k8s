package com.example.demokafkasecond.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DemoKafkaListener {

    @KafkaListener(id = "myListener2", topics = "myTopic",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listen(String data) {
        System.out.println("Received: " + data);
    }
}
