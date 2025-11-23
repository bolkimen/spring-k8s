package com.bolkimen.microservice.service_b.service;

import com.bolkimen.microservice.service_b.dto.BRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ServiceBService {

    public Flux<BRecord> listAllBRecords() {
        return Flux.just("Hello", "world", "Error")
                .map(str -> new BRecord(str));
    }
}
