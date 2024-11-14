package com.bank.microserviceCustomer.Model.entity;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Data
@Builder
@Document(collection = "cliente")
public class CustomerEntity implements Serializable {

    @Id
    private String id; // Identificador único para MongoDB
    private String type; // Tipo de cliente: puede ser "PERSONAL" o "BUSINESS"

    // Campos comunes para ambos tipos de clientes (personal y empresarial)
    private String name; // Nombre del cliente
    private String address; // Dirección del cliente

    // Campos específicos para clientes personales
    private String identificationNumber; // Número de identificación, solo para clientes de tipo PERSONAL

    // Campos específicos para clientes empresariales
    private String businessName; // Nombre de la empresa, solo para clientes de tipo BUSINESS
    private String registrationNumber; // Número de registro de la empresa, solo para clientes de tipo BUSINESS
    private String contactName; // Nombre del contacto principal, solo para clientes de tipo BUSINESS

}
