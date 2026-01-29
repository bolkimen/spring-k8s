package com.bolkimen.microservice.service_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/api/servicea/**")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("lb://service-a"))
				.route(p -> p
						.path("/api/serviceb/**")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("lb://service-b"))
				.route(p -> p
						.path("/login")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("lb://service-auth-non-reactive"))
				.build();
	}

}
