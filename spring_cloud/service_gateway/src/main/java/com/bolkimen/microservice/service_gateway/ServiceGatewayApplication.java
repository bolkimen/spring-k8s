package com.bolkimen.microservice.service_gateway;

import com.bolkimen.microservice.service_gateway.filter.BeforeRedirectionFilter;
import com.bolkimen.microservice.service_gateway.filter.RedirectionFilter;
import com.bolkimen.microservice.service_gateway.filter.redirect.CustomRequestFilter;
import com.bolkimen.microservice.service_gateway.filter.redirect.CustomResponseFilter;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	BeforeRedirectionFilter beforeRedirectionFilter;

	@Autowired
	RedirectionFilter redirectionFilter;

	@Autowired
	CustomResponseFilter customResponseFilter;

	@Autowired
	CustomRequestFilter customRequestFilter;

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
						.path("/auth-login/**")
						.filters(spec -> spec.rewritePath("/auth-login/(?<segment>.*)", "/${segment}"))
						.uri("lb://service-auth-login"))
				.route("auth-app", p -> p
						.path("/auth-oauth/**")
						//.and().method("GET", "POST",  "PUT", "DELETE")
						//.and().host("localhost*")
						.filters(f -> f.rewritePath("/auth-oauth/(?<segment>.*)", "/${segment}")
								.filters(
												redirectionFilter.apply(new RedirectionFilter.RedirectionFilterConfig()),
												beforeRedirectionFilter.apply(new BeforeRedirectionFilter.Config("auth-app")),
												customResponseFilter
												//customRequestFilter
										)
								.addRequestHeader("Hello", "World")
						)
						.uri("lb://service-auth-non-reactive"))
				.build();
	}

}
