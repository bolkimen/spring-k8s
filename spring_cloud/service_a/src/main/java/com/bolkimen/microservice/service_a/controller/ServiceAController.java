package com.bolkimen.microservice.service_a.controller;

import com.bolkimen.microservice.service_a.dto.ABRecord;
import com.bolkimen.microservice.service_a.dto.ARecord;
import com.bolkimen.microservice.service_a.service.ServiceAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/servicea")
public class ServiceAController {
    @Autowired
    ServiceAService serviceAService;

    @GetMapping("/auth")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }

    @GetMapping("/message")
    public String message() {
        return "secret message";
    }

    @PostMapping("/message")
    public String createMessage(@RequestBody String message) {
        return String.format("Message was created. Content: %s", message);
    }

    @GetMapping
    public Flux<ARecord> listAllARecords() {
        return serviceAService.listAllARecords();
    }

    @GetMapping("/ab")
    public Flux<ABRecord> listAllAandBRecords() {
        return serviceAService.listAllAandBRecords();
    }
}
