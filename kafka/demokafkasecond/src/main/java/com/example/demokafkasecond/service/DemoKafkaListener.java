package com.example.demokafkasecond.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    public void listenWordCount(ConsumerRecord<String, Long> record) {
        String word = record.key();
        Long count = -1L;//record.value();
        System.out.println("Topic: '%s'. Word: '%s' count: %s: ".formatted(wordcountOutputTopic, word, count));
    }
}
