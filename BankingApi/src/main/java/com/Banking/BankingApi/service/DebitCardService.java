package com.Banking.BankingApi.service;

import reactor.core.publisher.Mono;

import java.util.List;
// Interfaz para el servicio de tarjetas de débito
public interface DebitCardService {

     // Método para enlazar una tarjeta de débito a múltiples cuentas bancarias
     Mono<Void> linkDebitCardToAccounts(String cardId, List<String> accountIds);

     // Método para procesar una transacción con tarjeta de débito
     Mono<Void> processDebitCardTransaction(String cardId, double amount);
}
