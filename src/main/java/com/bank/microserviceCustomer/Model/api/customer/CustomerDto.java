package com.bank.microserviceCustomer.Model.api.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {
    private String id;
    private String type; // "PERSONAL" o "BUSINESS"
    private String name;
    private String address;

    // Campos específicos para clientes personales
    private String identificationNumber;

    // Campos específicos para clientes empresariales
    private String businessName;
    private String registrationNumber;
    private String contactName;
}
