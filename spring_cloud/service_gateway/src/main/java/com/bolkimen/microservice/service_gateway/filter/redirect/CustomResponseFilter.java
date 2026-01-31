package com.bolkimen.microservice.service_gateway.filter.redirect;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class CustomResponseFilter implements GatewayFilter, Ordered {
    private static final String LOCATION_HEADER_KEY = "Location";
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            try {
                if (exchange.getResponse().getHeaders().containsKey(LOCATION_HEADER_KEY)) {
                    String targetUri = exchange.getResponse().getHeaders().get(LOCATION_HEADER_KEY).get(0);

                    //MODIFY RESPONSE LOCATION ADDRESS
                    String modifiedTarget = "http://localhost:8073/";

                    exchange.getResponse().getHeaders().setLocation(new URI(modifiedTarget));
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }));
    }
}
