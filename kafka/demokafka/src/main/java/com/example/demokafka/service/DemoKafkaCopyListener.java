package com.example.demokafka.service;

import com.example.demokafka.dto.Greeting;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class DemoKafkaCopyListener {
    private Random rand = new Random();

    @KafkaListener(id = "myListenerCopy", topics = "${spring.kafka.topic-name}",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:1}",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listen(ConsumerRecord<?, ?> consumerRecord,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                       Acknowledgment ack) {
        System.out.println(
                "ReceivedCopy Message: " + consumerRecord.value()
                        + "from partition: " + partition);
        int n = rand.nextInt(50);
        if (n > 40) {
            System.out.println("Simulating processing failure for message: " + consumerRecord.value());
            ack.nack(Duration.ofMillis(500)); // Nack the message to reprocess it later
            return;
        }
        ack.acknowledge();
    }

    /*@KafkaListener(id = "myListenerCopyGreeting", topics = "${spring.kafka.topic-name}",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:1}",
            containerFactory = "greetingKafkaListenerContainerFactory")
    public void listenGreeting(@Payload Greeting greeting,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        System.out.println(
                "ReceivedCopyGreeting Message: " + greeting.getMsg()
                        + "from partition: " + partition);
    }*/
}
