package com.bank.microserviceCustomer.controller;

import com.bank.microserviceCustomer.Model.api.customer.CustomerDto;
import com.bank.microserviceCustomer.Model.api.customer.CustomerRequest;
import com.bank.microserviceCustomer.Model.api.shared.ResponseDto;
import com.bank.microserviceCustomer.Model.api.shared.ResponseDtoBuilder;

import com.bank.microserviceCustomer.business.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final ICustomerService customerService;
    @Operation(summary = "Crear cliente personal", description = "Crea un cliente personal con los datos proporcionados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente personal creado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/personal")
    public Mono<ResponseDto<CustomerDto>> createPersonalCustomer(@RequestBody CustomerRequest request) {
        return customerService.createPersonalCustomer(request)
                .map(customer -> ResponseDtoBuilder.success(customer, "Cliente personal creado"));
    }
    @Operation(summary = "Crear cliente empresarial", description = "Crea un cliente empresarial con los datos proporcionados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente empresarial creado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/business")
    public Mono<ResponseDto<CustomerDto>> createBusinessCustomer(@RequestBody CustomerRequest request) {
        return customerService.createBusinessCustomer(request)
                .map(customer -> ResponseDtoBuilder.success(customer, "Cliente empresarial creado"));
    }
    @Operation(summary = "Obtener cliente por ID", description = "Obtiene los detalles de un cliente específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public Mono<ResponseDto<CustomerDto>> getCustomerById(@PathVariable String id) {
        return customerService.findById(id)
                .map(customer -> ResponseDtoBuilder.success(customer, "Cliente encontrado"))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cliente no encontrado"));
    }
    @Operation(summary = "Obtener todos los clientes", description = "Obtiene una lista de todos los clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public Flux<ResponseDto<CustomerDto>> getAllCustomers() {
        return customerService.findAll()
                .map(customer -> ResponseDtoBuilder.success(customer, "Lista de clientes obtenida"));
    }
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public Mono<ResponseDto<CustomerDto>> updateCustomer(@PathVariable String id, @RequestBody CustomerRequest request) {
        return customerService.updateCustomer(id, request)
                .map(updatedCustomer -> ResponseDtoBuilder.success(updatedCustomer, "Cliente actualizado"))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cliente no encontrado"));
    }
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente eliminado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseDto<Object>> deleteCustomer(@PathVariable String id) {
        return customerService.deleteById(id)
                .then(Mono.just(ResponseDtoBuilder.success(null, "Cliente eliminado")))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cliente no encontrado"));
    }

    @GetMapping("/{id}/has-credit-card")
    public Mono<Boolean> hasActiveCreditCard(@PathVariable String id) {
        return customerService.hasActiveCreditCard(id)
                .onErrorResume(error -> {
                    log.error("Error al verificar tarjeta de crédito para cliente ID: {}. Error: {}", id, error.getMessage());
                    return Mono.just(false); // Devuelve falso si ocurre un error
                });
    }
}
