package com.Banking.BankingApi.controller;


import com.Banking.BankingApi.service.DebitCardService;
import com.Banking.BankingApi.service.impl.DebitCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
// Controlador para manejar solicitudes relacionadas con tarjetas de débito

public class DebitCardController  {
    @Autowired
    private DebitCardServiceImpl debitCardService;
    // Endpoint para enlazar una tarjeta de débito a múltiples cuentas bancarias
    @PostMapping("/{cardId}/link-accounts")
    public Mono<ResponseEntity<Object>> linkDebitCardToAccounts(@PathVariable String cardId, @RequestBody List<String> accountIds) {
        return debitCardService.linkDebitCardToAccounts(cardId, accountIds)
                .then(Mono.just(ResponseEntity.ok().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    // Endpoint para procesar una transacción con tarjeta de débito
    @PostMapping("/{cardId}/process-transaction")
    public Mono<ResponseEntity<Object>> processDebitCardTransaction(@PathVariable String cardId, @RequestBody List<String> accountIds) {
        return debitCardService.linkDebitCardToAccounts(cardId, accountIds)
                .then(Mono.just(ResponseEntity.ok().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}