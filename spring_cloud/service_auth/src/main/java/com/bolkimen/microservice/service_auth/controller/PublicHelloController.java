package com.bolkimen.microservice.service_auth.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Collections;


@RestController
@RequestMapping("/public")
public class PublicHelloController {
    @GetMapping("/hello")
    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        return serverRequest.principal()
                .map(Principal::getName)
                .flatMap((username) ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Collections.singletonMap("message", "Hello " + username + "!"))
                );
    }
}
