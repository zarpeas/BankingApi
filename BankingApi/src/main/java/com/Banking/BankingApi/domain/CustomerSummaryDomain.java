package com.Banking.BankingApi.domain;

import com.Banking.BankingApi.model.BankAccount;
import com.Banking.BankingApi.model.Credit;
import com.Banking.BankingApi.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CustomerSummaryDomain {
        private Customer customer;
        private List<BankAccount> bankAccounts;
        private List<Credit> credits;
    }
