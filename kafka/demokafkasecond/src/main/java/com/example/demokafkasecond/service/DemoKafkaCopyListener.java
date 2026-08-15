package com.example.demokafkasecond.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DemoKafkaCopyListener {
    @KafkaListener(id = "myListenerCopy", topics = "test-topic",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:1}")
    public void listen(String data) {
        System.out.println("ReceivedCopy: " + data);
    }
}
