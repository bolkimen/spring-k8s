package com.bolkimen.microservice.service_a.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean("defaultWebClientBuilder")
    //@LoadBalanced
    public WebClient.Builder defaultWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean("serviceBClient")
    public WebClient serviceBWebClient(@Qualifier("defaultWebClientBuilder") WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://127.0.0.1:8091").build();
    }
}
