package com.example.demokafkasecond.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DemoKafkaListener {
    @Value(value = "${spring.kafka.wordcount-output-topic}")
    private String wordcountOutputTopic;

    @KafkaListener(id = "myListener", topics = "streamingTopic22",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listen(String data) {
        System.out.println("streamingTopic22: " + data);
    }

    @KafkaListener(id = "myListenerWordCount", topics = "${spring.kafka.wordcount-output-topic}",
            autoStartup = "${listen.auto.start:true}")
    public void listenWordCount(String data) {
        System.out.println("Word count '%s': ".formatted(wordcountOutputTopic) + data);
    }
}
