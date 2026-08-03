package com.example.demokafka.service;

import org.springframework.kafka.annotation.KafkaListener;

public class DemoKafkaListener {

    @KafkaListener(id = "myListener", topics = "myTopic",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listen(String data) {
        data.toString();
    // ...
    }
}
