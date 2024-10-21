package com.Banking.BankingApi.repository;

import com.Banking.BankingApi.domain.DebitCardDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DebitCardRepository extends ReactiveMongoRepository<DebitCardDomain, String> {
    Mono<DebitCardDomain> findById(String cardId);

    Mono<Object> save(Void card);
}
