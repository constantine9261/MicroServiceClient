package com.bank.microserviceCustomer.Model.api.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsolidatedCustomerSummary {
    private String customerId;
    private List<AccountDto> accounts;
    private List<CreditDto> credits;
}
