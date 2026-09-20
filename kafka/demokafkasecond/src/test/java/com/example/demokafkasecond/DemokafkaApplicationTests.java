package com.example.demokafkasecond;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EmbeddedKafka(
		partitions = 1,
		topics = "customers",
		bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
class DemokafkaApplicationTests {

	@Autowired
	private Environment environment;

	@Test
	void printKafkaBootstrapServer() {
		System.out.println(
				"BOOTSTRAP = " +
						environment.getProperty("spring.kafka.bootstrap-servers")
		);
	}

	@Test
	void contextLoads() {
	}

}
