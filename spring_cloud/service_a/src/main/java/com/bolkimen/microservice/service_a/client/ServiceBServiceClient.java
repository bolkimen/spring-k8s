package com.bolkimen.microservice.service_a.client;

import com.bolkimen.microservice.service_a.dto.BRecord;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ServiceBServiceClient {
    private final WebClient webClient;

    public ServiceBServiceClient(@Qualifier("serviceBClient") WebClient webClient){
        this.webClient=webClient;
    }

    public Flux<BRecord> getBRecord(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.pathSegment("api","serviceb").build())
                .retrieve()
                .bodyToFlux(BRecord.class);
                //.switchIfEmpty(Flux.error(new NotFoundException("No records")))
                //.onErrorResume(err->Flux.error(new NotFoundException("No records")));
    }
}
