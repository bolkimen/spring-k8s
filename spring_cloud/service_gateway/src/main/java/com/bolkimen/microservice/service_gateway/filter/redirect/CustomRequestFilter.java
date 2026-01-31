package com.bolkimen.microservice.service_gateway.filter.redirect;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Component
public class CustomRequestFilter implements GatewayFilter, Ordered {

    @Override
    public int getOrder() {
        return 10001;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String target = exchange.getRequest().getURI().toString();

            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, new URI(target));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }
}
