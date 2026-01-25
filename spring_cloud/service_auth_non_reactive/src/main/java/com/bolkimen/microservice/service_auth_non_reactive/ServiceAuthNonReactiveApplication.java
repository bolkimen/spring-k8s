package com.bolkimen.microservice.service_auth_non_reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceAuthNonReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAuthNonReactiveApplication.class, args);
	}

}
