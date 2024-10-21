package com.Banking.BankingApi.service;

import com.Banking.BankingApi.domain.BankProductReport;
import com.Banking.BankingApi.domain.TransactionDomain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface ReportService {
    // Método para obtener las últimas 10 transacciones de una tarjeta
    Flux<TransactionDomain> getLast10Transactions(String cardId);

    // Método para generar un reporte de producto bancario
     Mono<BankProductReport> generateProductReport(String productId, LocalDateTime startTime, LocalDateTime endTime);
}
