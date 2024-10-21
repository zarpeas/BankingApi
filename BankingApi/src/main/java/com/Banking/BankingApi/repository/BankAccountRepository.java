package com.Banking.BankingApi.repository;

import com.Banking.BankingApi.domain.BankAccountDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface BankAccountRepository extends ReactiveMongoRepository<BankAccountDomain, String> {
    Flux<BankAccountDomain> findByCustomerId(String customerId);

}
