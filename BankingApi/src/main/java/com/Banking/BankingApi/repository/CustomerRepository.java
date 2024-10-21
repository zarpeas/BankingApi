package com.Banking.BankingApi.repository;

import com.Banking.BankingApi.domain.CustomerDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerDomain, String> {
    Mono<CustomerDomain> findByCustomer(String id);
}
