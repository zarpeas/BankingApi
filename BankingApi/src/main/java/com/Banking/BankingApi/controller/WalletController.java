package com.Banking.BankingApi.controller;


import com.Banking.BankingApi.domain.PaymentTransactionDomain;
import com.Banking.BankingApi.domain.WalletDomain;
import com.Banking.BankingApi.service.impl.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    @Autowired
    private WalletServiceImpl walletService;

    @PostMapping("/register")
    public Mono<ResponseEntity<WalletDomain>> registerWallet(@RequestBody WalletDomain wallet) {
        return walletService.registerUser(wallet)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/link-debit-card")
    public Mono<ResponseEntity<WalletDomain>> linkDebitCard(@RequestParam String phoneNumber, @RequestParam String debitCard) {
        return walletService.linkDebitCard(phoneNumber, debitCard)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/paymentTransactionDomain")
    public Mono<ResponseEntity<PaymentTransactionDomain>> processTransaction(@RequestParam String fromPhoneNumber, @RequestParam String toPhoneNumber, @RequestParam double amount) {
        return walletService.sendPayment(fromPhoneNumber, toPhoneNumber, amount)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


}
