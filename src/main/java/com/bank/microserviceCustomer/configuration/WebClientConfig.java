package com.bank.microserviceCustomer.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    // Define el WebClient como un bean para el CustomerService
    @Bean
    @Qualifier("creditServiceWebClient")
    public WebClient creditServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083/api/credits") // Base URL del microservicio de créditos
                .build();
    }

    @Bean
    @Qualifier("customerServiceWebClient")
    public WebClient customerServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082/customers") // Base URL del microservicio de clientes
                .build();
    }
}
