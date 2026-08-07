package com.example.demokafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DemoKafkaCopyListener {
    @KafkaListener(id = "myListenerCopy", topics = "myTopic",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listen(String data) {
        System.out.println("ReceivedCopy: " + data);
    }
}
