package com.example.demokafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DemoKafkaStreamListener {
    @KafkaListener(id = "outputOrderName", topics = "${spring.kafka.output-order-name}",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:1}",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listen(ConsumerRecord<?, ?> consumerRecord,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                       Acknowledgment ack) {
        System.out.println(
                "outputOrderName Message: " + consumerRecord.value()
                        + "from partition: " + partition);
        ack.acknowledge();
    }
}
