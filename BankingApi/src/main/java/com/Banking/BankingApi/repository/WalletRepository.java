package com.Banking.BankingApi.repository;

import com.Banking.BankingApi.domain.WalletDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface WalletRepository extends ReactiveMongoRepository<WalletDomain, String> {
    Mono<WalletDomain> findByPhoneNumber(String phoneNumber);
}
