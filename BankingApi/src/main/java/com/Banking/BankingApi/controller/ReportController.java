package com.Banking.BankingApi.controller;

import com.Banking.BankingApi.domain.BankProductReport;
import com.Banking.BankingApi.domain.TransactionDomain;
import com.Banking.BankingApi.service.ReportService;
import com.Banking.BankingApi.service.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
@RestController
// Controlador para manejar solicitudes de reportes
public class ReportController {

    @Autowired
    private ReportServiceImpl reportService; // Inyección de dependencia del servicio de reportes

    // Endpoint para generar un reporte de producto bancario
    @GetMapping("/product-report")
    public Mono<ResponseEntity<BankProductReport>> generateProductReport(@RequestParam String productId,
                                                                         @RequestParam LocalDateTime startTime,
                                                                         @RequestParam LocalDateTime endTime) {
        // Llama al servicio de reportes para generar un reporte basado en el ID del producto y un intervalo de tiempo
        return reportService.generateProductReport(productId, startTime, endTime)
                .map(ResponseEntity::ok) // Envuelve el reporte en una respuesta HTTP 200 OK
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Devuelve 404 si no se encuentra el reporte
    }

    // Endpoint para obtener las últimas 10 transacciones de una tarjeta
    @GetMapping("/{cardId}/last-transactions")
    public Flux<TransactionDomain> getLast10Transactions(@PathVariable String cardId) {
        // Llama al servicio de reportes para obtener las últimas 10 transacciones de la tarjeta especificada
        return reportService.getLast10Transactions(cardId);
    }
}
