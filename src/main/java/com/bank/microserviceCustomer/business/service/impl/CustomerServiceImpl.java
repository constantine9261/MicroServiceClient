package com.bank.microserviceCustomer.business.service.impl;

import com.bank.microserviceCustomer.Model.api.customer.CustomerDto;
import com.bank.microserviceCustomer.Model.api.customer.CustomerRequest;
import com.bank.microserviceCustomer.Model.entity.CustomerEntity;
import com.bank.microserviceCustomer.business.repository.ICustomerRepository;
import com.bank.microserviceCustomer.business.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j // Para habilitar los logs con SLF4J
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

    // Método para convertir CustomerRequest a CustomerEntity
    private CustomerEntity convertToEntity(CustomerRequest request) {
        log.debug("Convirtiendo CustomerRequest a CustomerEntity");
        return CustomerEntity.builder()
                .type(request.getType())
                .name(request.getName())
                .address(request.getAddress())
                .identificationNumber("PERSONAL".equals(request.getType()) ? request.getIdentificationNumber() : null)
                .businessName("BUSINESS".equals(request.getType()) ? request.getBusinessName() : null)
                .registrationNumber("BUSINESS".equals(request.getType()) ? request.getRegistrationNumber() : null)
                .contactName("BUSINESS".equals(request.getType()) ? request.getContactName() : null)
                .build();
    }

    // Método para convertir CustomerEntity a CustomerDto
    private CustomerDto convertToDto(CustomerEntity entity) {
        return CustomerDto.builder()
                .id(entity.getId()) // Incluyendo el id en el DTO
                .type(entity.getType())
                .name(entity.getName())
                .address(entity.getAddress())
                .identificationNumber("PERSONAL".equals(entity.getType()) ? entity.getIdentificationNumber() : null)
                .businessName("BUSINESS".equals(entity.getType()) ? entity.getBusinessName() : null)
                .registrationNumber("BUSINESS".equals(entity.getType()) ? entity.getRegistrationNumber() : null)
                .contactName("BUSINESS".equals(entity.getType()) ? entity.getContactName() : null)
                .build();
    }

    @Override
    public Mono<CustomerDto> createPersonalCustomer(CustomerRequest request) {
        log.info("Creando un nuevo cliente personal");
        CustomerEntity customerEntity = convertToEntity(request);
        return customerRepository.save(customerEntity)
                .map(this::convertToDto)
                .doOnSuccess(dto -> log.info("Cliente personal creado con id: {}", dto.getId()))
                .doOnError(error -> log.error("Error al crear cliente personal: {}", error.getMessage()));
    }

    @Override
    public Mono<CustomerDto> createBusinessCustomer(CustomerRequest request) {
        CustomerEntity customerEntity = convertToEntity(request);
        return customerRepository.save(customerEntity)
                .map(this::convertToDto)
                .doOnSuccess(dto -> log.info("Cliente empresarial creado con id: {}", dto.getId()))
                .doOnError(error -> log.error("Error al crear cliente empresarial: {}", error.getMessage()));
    }

    @Override
    public Mono<CustomerDto> findById(String id) {
        return customerRepository.findById(id)
                .map(this::convertToDto)
                .doOnSuccess(dto -> log.info("Cliente encontrado: {}", dto))
                .doOnError(error -> log.error("Error al buscar cliente con id {}: {}", id, error.getMessage()));
    }

    @Override
    public Flux<CustomerDto> findAll() {
        return customerRepository.findAll()
                .map(this::convertToDto)
                .doOnComplete(() -> log.info("Todos los clientes han sido obtenidos"))
                .doOnError(error -> log.error("Error al obtener la lista de clientes: {}", error.getMessage()));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return customerRepository.findById(id)
                .flatMap(customer -> customerRepository.delete(customer)
                        .doOnSuccess(unused -> log.info("Cliente con id {} eliminado exitosamente", id)))
                .doOnError(error -> log.error("Error al eliminar cliente con id {}: {}", id, error.getMessage()));
    }

    @Override
    public Mono<CustomerDto> updateCustomer(String id, CustomerRequest request) {
        return customerRepository.findById(id)
                .flatMap(existingCustomer -> {
                    // Actualización de datos según tipo de cliente
                    log.debug("Actualizando los datos del cliente");
                    existingCustomer.setName(request.getName());
                    existingCustomer.setAddress(request.getAddress());
                    existingCustomer.setType(request.getType());

                    if ("PERSONAL".equals(request.getType())) {
                        existingCustomer.setIdentificationNumber(request.getIdentificationNumber());
                        existingCustomer.setBusinessName(null);
                        existingCustomer.setRegistrationNumber(null);
                        existingCustomer.setContactName(null);
                    } else if ("BUSINESS".equals(request.getType())) {
                        existingCustomer.setBusinessName(request.getBusinessName());
                        existingCustomer.setRegistrationNumber(request.getRegistrationNumber());
                        existingCustomer.setContactName(request.getContactName());
                        existingCustomer.setIdentificationNumber(null);
                    }

                    return customerRepository.save(existingCustomer);
                })
                .map(this::convertToDto)
                .doOnSuccess(dto -> log.info("Cliente con id {} actualizado exitosamente", id))
                .doOnError(error -> log.error("Error al actualizar cliente con id {}: {}", id, error.getMessage()));
    }
}
