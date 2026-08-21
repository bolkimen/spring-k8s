package com.example.demokafka.service;

import com.example.demokafka.dto.Greeting;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
//@KafkaListener(id = "multiGroup", topics = "${spring.kafka.topic-name}", containerFactory = "greetingKafkaListenerContainerFactory")
public class MultiTypeKafkaListener {
    /*@KafkaHandler
    public void handleGreeting(Greeting greeting) {
        System.out.println("Greeting received: " + greeting);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Unkown type received: " + object);
    }*/
}
