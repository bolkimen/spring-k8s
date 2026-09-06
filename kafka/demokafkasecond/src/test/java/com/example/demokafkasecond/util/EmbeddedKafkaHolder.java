package com.example.demokafkasecond.util;

import org.springframework.kafka.KafkaException;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;

public final class EmbeddedKafkaHolder {
    private static EmbeddedKafkaBroker embeddedKafka = new EmbeddedKafkaKraftBroker(1, 2, "test-topic")
            .brokerListProperty("spring.kafka.bootstrap-servers");

    private static volatile boolean started;

    public static EmbeddedKafkaBroker getEmbeddedKafka() {
        if (!started) {
            synchronized (EmbeddedKafkaBroker.class) {
                try {
                    embeddedKafka.afterPropertiesSet();
                }
                catch (Exception e) {
                    throw new KafkaException("Embedded broker failed to start", e);
                }
                started = true;
            }
        }
        return embeddedKafka;
    }
}
