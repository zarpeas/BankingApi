package com.Banking.BankingApi.repository;

import com.Banking.BankingApi.domain.PaymentTransactionDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PaymentTransactionRepository extends ReactiveMongoRepository<PaymentTransactionDomain, String> {
}
