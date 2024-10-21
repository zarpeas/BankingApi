package com.Banking.BankingApi.service.impl;

import com.Banking.BankingApi.domain.BankProductReport;
import com.Banking.BankingApi.domain.TransactionDomain;
import com.Banking.BankingApi.repository.DebitCardRepository;
import com.Banking.BankingApi.service.ReportService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
// Implementación del servicio de reportes
public class ReportServiceImpl implements ReportService {

    private DebitCardRepository debitCardRepository;  // Repositorio para acceder a los datos de créditos

    // Método para obtener las últimas 10 transacciones de una tarjeta
    @Override
    public Flux<TransactionDomain> getLast10Transactions(String cardId) {
        return debitCardRepository.findById(cardId).flatMap(card->
                Flux.fromIterable(card.getTransactions())).sort((t1, t2) ->t2.getDate.compareTo(t1.getDate()))
                .take(10);
    }
    // Método para generar un reporte de producto bancario
    @Override
    public Mono<BankProductReport> generateProductReport(String productId, LocalDateTime startTime, LocalDateTime endTime) {
        return Mono.just(new BankProductReport());
    }
}
