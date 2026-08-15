package com.example.demokafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DemoKafkaCopyListener {
    @KafkaListener(id = "myListenerCopy", topics = "${spring.kafka.topic-name}",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:1}",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listen(@Payload String message,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        System.out.println(
                "ReceivedCopy Message: " + message
                        + "from partition: " + partition);
    }
}
