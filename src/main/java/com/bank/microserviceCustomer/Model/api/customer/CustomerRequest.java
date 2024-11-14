package com.bank.microserviceCustomer.Model.api.customer;

import lombok.Data;

@Data
public class CustomerRequest {

    private String type; // Tipo de cliente: "PERSONAL" o "BUSINESS"
    private String name; // Nombre del cliente
    private String address; // Dirección del cliente

    // Campos específicos para clientes personales
    private String identificationNumber; // Número de identificación del cliente personal

    // Campos específicos para clientes empresariales
    private String businessName; // Nombre de la empresa
    private String registrationNumber; // Número de registro de la empresa
    private String contactName; // Contacto principal de la empresa
}
