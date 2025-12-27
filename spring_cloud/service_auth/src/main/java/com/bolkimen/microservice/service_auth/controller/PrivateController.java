package com.bolkimen.microservice.service_auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PrivateController {
    @GetMapping("/private/hello")
    public Mono<String> privateHello() {
        return Mono.just("Hello, this is a private endpoint!");
    }
}
