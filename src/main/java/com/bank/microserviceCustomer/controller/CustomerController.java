package com.bank.microserviceCustomer.controller;

import com.bank.microserviceCustomer.Model.api.customer.CustomerDto;
import com.bank.microserviceCustomer.Model.api.customer.CustomerRequest;
import com.bank.microserviceCustomer.Model.api.shared.ResponseDto;
import com.bank.microserviceCustomer.Model.api.shared.ResponseDtoBuilder;

import com.bank.microserviceCustomer.business.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @PostMapping("/personal")
    public Mono<ResponseDto<CustomerDto>> createPersonalCustomer(@RequestBody CustomerRequest request) {
        return customerService.createPersonalCustomer(request)
                .map(customer -> ResponseDtoBuilder.success(customer, "Cliente personal creado"));
    }

    @PostMapping("/business")
    public Mono<ResponseDto<CustomerDto>> createBusinessCustomer(@RequestBody CustomerRequest request) {
        return customerService.createBusinessCustomer(request)
                .map(customer -> ResponseDtoBuilder.success(customer, "Cliente empresarial creado"));
    }

    @GetMapping("/{id}")
    public Mono<ResponseDto<CustomerDto>> getCustomerById(@PathVariable String id) {
        return customerService.findById(id)
                .map(customer -> ResponseDtoBuilder.success(customer, "Cliente encontrado"))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cliente no encontrado"));
    }

    @GetMapping
    public Flux<ResponseDto<CustomerDto>> getAllCustomers() {
        return customerService.findAll()
                .map(customer -> ResponseDtoBuilder.success(customer, "Lista de clientes obtenida"));
    }

    @PutMapping("/{id}")
    public Mono<ResponseDto<CustomerDto>> updateCustomer(@PathVariable String id, @RequestBody CustomerRequest request) {
        return customerService.updateCustomer(id, request)
                .map(updatedCustomer -> ResponseDtoBuilder.success(updatedCustomer, "Cliente actualizado"))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cliente no encontrado"));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseDto<Object>> deleteCustomer(@PathVariable String id) {
        return customerService.deleteById(id)
                .then(Mono.just(ResponseDtoBuilder.success(null, "Cliente eliminado")))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cliente no encontrado"));
    }

}
