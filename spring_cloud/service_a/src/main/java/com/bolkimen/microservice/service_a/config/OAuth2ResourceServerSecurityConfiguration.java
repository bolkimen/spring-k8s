package com.bolkimen.microservice.service_a.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class OAuth2ResourceServerSecurityConfiguration {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers(HttpMethod.GET, "/api/servicea/message**").hasAuthority("SCOPE_message:read")
                        .pathMatchers(HttpMethod.POST, "/api/servicea/message**").hasAuthority("SCOPE_message:write")
                        .pathMatchers("/api/servicea").permitAll()
                        .pathMatchers("/api/servicea/ab").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(withDefaults())
                );
        // @formatter:on
        return http.build();
    }
}
