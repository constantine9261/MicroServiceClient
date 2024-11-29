package com.bank.microserviceCustomer.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @Primary // Este será el WebClient predeterminado
    public WebClient creditServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083/api/credits")
                .build();
    }

    @Bean
    public WebClient customerServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082/customers")
                .build();
    }

    @Bean
    public WebClient accountWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/accounts")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
