package com.bolkimen.microservice.service_a.controller;

import com.bolkimen.microservice.service_a.dto.ABRecord;
import com.bolkimen.microservice.service_a.dto.ARecord;
import com.bolkimen.microservice.service_a.service.ServiceAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/servicea")
public class ServiceAController {
    @Autowired
    ServiceAService serviceAService;

    @GetMapping
    public Flux<ARecord> listAllARecords() {
        return serviceAService.listAllARecords();
    }

    @GetMapping("/ab")
    public Flux<ABRecord> listAllAandBRecords() {
        return serviceAService.listAllAandBRecords();
    }
}
