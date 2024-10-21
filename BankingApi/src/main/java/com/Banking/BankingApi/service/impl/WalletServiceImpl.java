package com.Banking.BankingApi.service.impl;

import com.Banking.BankingApi.domain.PaymentTransactionDomain;
import com.Banking.BankingApi.domain.WalletDomain;
import com.Banking.BankingApi.repository.PaymentTransactionRepository;
import com.Banking.BankingApi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class WalletServiceImpl {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    // Registro de usuario
    public Mono<WalletDomain> registerUser(WalletDomain wallet) {
        return walletRepository.save(wallet);
    }

    // Envío de pagos
    public Mono<PaymentTransactionDomain> sendPayment(String PhoneNumber, String toPhoneNumber, double amount) {
        return walletRepository.findByPhoneNumber(PhoneNumber)
                .flatMap(wallet -> {
                    if (wallet.getBalance() >= amount) {
                        wallet.setBalance(wallet.getBalance() - amount);
                        return walletRepository.save(wallet)
                                .then(walletRepository.findByPhoneNumber(toPhoneNumber))
                                .flatMap(receiverWallet -> {
                                    receiverWallet.setBalance(receiverWallet.getBalance() + amount);
                                    return walletRepository.save(receiverWallet)
                                            .then(paymentTransactionRepository.
                                                    save(new PaymentTransactionDomain(PhoneNumber, toPhoneNumber, amount, LocalDateTime.now(), "COMPLETED")));
                                });
                    } else {
                        return Mono.error(new IllegalStateException("Saldo insuficiente"));
                    }
                });
    }

    // Asociación con tarjeta de débito
    public Mono<WalletDomain> linkDebitCard(String phoneNumber, String debitCardId) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .flatMap(wallet -> {
                    wallet.setLinkedDebitCardId(debitCardId);
                    return walletRepository.save(wallet);
                });
    }

}
