package com.Banking.BankingApi.service.impl;

import com.Banking.BankingApi.domain.DebitCardDomain;
import com.Banking.BankingApi.repository.BankAccountRepository;
import com.Banking.BankingApi.repository.DebitCardRepository;
import com.Banking.BankingApi.service.DebitCardService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
// Implementación del servicio de tarjetas de débito
public class DebitCardServiceImpl implements DebitCardService {

    @Autowired
    private DebitCardRepository debitCardRepository;  // Repositorio para acceder a los datos de créditos

    @Autowired
    private BankAccountRepository bankAccountRepository;  // Repositorio para acceder a los datos de cuentas bancarias

    // Método para enlazar una tarjeta de débito a varias cuentas bancarias
    @Override
    public Mono<Void> linkDebitCardToAccounts(String cardId, List<String> accountId) {
        return debitCardRepository.findById(cardId).flatMap(card ->{
            card.getLinkedAccountIds().addAll(accountId);
            return debitCardRepository.save(card).then();
        });
    }
    // Método para procesar una transacción con tarjeta de débito
    @Override
    public Mono<Void> processDebitCardTransaction(String cardId, double amount) {
        return debitCardRepository.findById(cardId)
                .flatMap(card ->
                        bankAccountRepository.findById(card.getPrimaryAccountId())
                                .flatMap(primaryAccount -> {
                                    if (primaryAccount.getBalance() >= amount) {
                                        primaryAccount.setBalance(primaryAccount.getBalance() - amount);
                                        return bankAccountRepository.save(primaryAccount).then();
                                    } else {
                                        return processFallbackAccounts(card, amount);
                                    }
                                })
                );
    }

    // Método para procesar transacciones utilizando cuentas
    private Mono<Void> processFallbackAccounts(DebitCardDomain card, double amount) {
        return Flux.fromIterable(card.getLinkedAccountIds())
                .concatMap(accountId -> bankAccountRepository.findById(accountId))
                .filter(account -> account.getBalance() >= amount)
                .next()
                .flatMap(account -> {
                    account.setBalance(account.getBalance() - amount);
                    return bankAccountRepository.save(account).then();
                });

    }
}