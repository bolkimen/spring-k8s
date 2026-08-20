package com.example.demokafka.service;

import com.example.demokafka.dto.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class BackgroundProcessor {

    @Autowired
    private KafkaTemplate greetingKafkaTemplate;

    @Value(value = "${spring.kafka.topic-name}")
    private String topicName;

    @Scheduled(fixedRate = 600)
    public void messageSenderProcessor() {
        System.out.println("Running file cleanup...");
        // logic here
        sendMessage("Hello from BackgroundProcessor!");
        sendMessage("Hello World!");
        greetingKafkaTemplate.send(topicName, new Greeting("Hello", "World"));
    }

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, String>> future = greetingKafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }
}
