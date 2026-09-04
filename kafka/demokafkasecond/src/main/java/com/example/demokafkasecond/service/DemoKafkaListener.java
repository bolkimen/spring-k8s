package com.example.demokafkasecond.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DemoKafkaListener {

    @KafkaListener(id = "myListener", topics = "streamingTopic22",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listen(String data) {
        System.out.println("streamingTopic22: " + data);
    }
}
