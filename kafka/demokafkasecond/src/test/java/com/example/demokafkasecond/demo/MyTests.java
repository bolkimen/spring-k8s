package com.example.demokafkasecond.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@EmbeddedKafka(
        partitions = 5,
        topics = {"cat", "hat"}
)
public class MyTests {
    @Autowired
    private EmbeddedKafkaBroker broker;

    @Test
    public void test() {
        broker.addTopics(new NewTopic("thing1", 10, (short) 1), new NewTopic("thing2", 15, (short) 1));
    }
}
