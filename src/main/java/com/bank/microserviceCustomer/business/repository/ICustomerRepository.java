package com.bank.microserviceCustomer.business.repository;


import com.bank.microserviceCustomer.Model.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ICustomerRepository extends
        ReactiveMongoRepository<CustomerEntity, String> {

}
