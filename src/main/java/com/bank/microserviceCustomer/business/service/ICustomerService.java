package com.bank.microserviceCustomer.business.service;


import com.bank.microserviceCustomer.Model.api.customer.ConsolidatedCustomerSummary;
import com.bank.microserviceCustomer.Model.api.customer.CustomerDto;
import com.bank.microserviceCustomer.Model.api.customer.CustomerRequest;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Mono<CustomerDto> createPersonalCustomer(CustomerRequest request);

    Mono<CustomerDto> createBusinessCustomer(CustomerRequest request);

    Mono<CustomerDto> findById(String id);

    Flux<CustomerDto> findAll();

    Mono<Void> deleteById(String id);

    Mono<CustomerDto> updateCustomer(String id, CustomerRequest request);

    Mono<Boolean> hasActiveCreditCard(String customerId);


    Mono<ConsolidatedCustomerSummary> getCustomerSummary(String customerId);

}