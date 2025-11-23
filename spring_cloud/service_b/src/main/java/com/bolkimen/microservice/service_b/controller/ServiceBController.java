package com.bolkimen.microservice.service_b.controller;

import com.bolkimen.microservice.service_b.dto.BRecord;
import com.bolkimen.microservice.service_b.service.ServiceBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/serviceb")
public class ServiceBController {
    @Autowired
    ServiceBService serviceBService;

    @GetMapping
    public Flux<BRecord> listAllBRecords() {
        return serviceBService.listAllBRecords();
    }
}
