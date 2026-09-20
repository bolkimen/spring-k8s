package com.example.demokafkasecond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * bootstrap servers: localhost:9092
 */
@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
@EnableScheduling
public class DemokafkaSecondApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemokafkaSecondApplication.class, args);
	}

}
