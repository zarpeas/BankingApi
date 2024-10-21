package com.Banking.BankingApi.repository;

import com.Banking.BankingApi.domain.CreditDomain;
import com.Banking.BankingApi.domain.CustomerDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepository extends ReactiveMongoRepository<CreditDomain, String> {
    Flux<CreditDomain> findByCredit(String id);

    Flux<Object> findByCustomerId(String customerId);
}
