package com.Banking.BankingApi.repository;

import com.Banking.BankingApi.domain.CustomerDomain;
import com.Banking.BankingApi.domain.TransactionDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveMongoRepository<TransactionDomain, String> {

    // Encuentra una transacción específica por su ID
    Mono<CustomerDomain> findByTransaction(String id);

    // Encuentra transacciones asociadas a un cliente, cuenta bancaria y crédito específicos
    Flux<TransactionDomain> findByCustomerIdAndAccountIdAndLoanId(String customerId, String accountId, String loanId);
}
