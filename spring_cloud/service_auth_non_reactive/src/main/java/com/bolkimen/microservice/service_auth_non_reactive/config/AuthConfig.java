package com.bolkimen.microservice.service_auth_non_reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class AuthConfig {
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // @formatter:off
        RegisteredClient webClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8092/login/oauth2/code/login-client")
                .scope(OidcScopes.OPENID)
                .scope("message:read")
                .scope("message:write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        RegisteredClient apiClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("josh")
                .clientSecret("{noop}control")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("message:read")
                .scope("message:write")
                .build();
        // @formatter:on

        return new InMemoryRegisteredClientRepository(webClient, apiClient);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails josh = User.withDefaultPasswordEncoder()
                .username("josh")
                .password("control")
                .roles("USER")
                .build();
        UserDetails marcus = User.withDefaultPasswordEncoder()
                .username("marcus")
                .password("password")
                .roles("USER")
                .build();
        UserDetails steve = User.withDefaultPasswordEncoder()
                .username("steve")
                .password("password")
                .roles("USER")
                .authorities("message:read")
                .build();
        return new InMemoryUserDetailsManager(josh, marcus, steve);
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService users) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/oauth2/token").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/oauth2/token"))
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .rememberMe((rememberMe) -> rememberMe.userDetailsService(users));
        // @formatter:on
        return http.build();
    }*/

}
