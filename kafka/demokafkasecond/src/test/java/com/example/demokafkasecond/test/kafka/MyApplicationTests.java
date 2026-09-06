package com.example.demokafkasecond.test.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@EmbeddedKafka(topics = "someTopic",
        bootstrapServersProperty = "spring.kafka.bootstrap-servers") // this is now the default
public class MyApplicationTests {
    @Autowired
    private KafkaTemplate<String, String> template;

    @Test
    void test() {
        template.send("someTopic", "llala");
    }
}
