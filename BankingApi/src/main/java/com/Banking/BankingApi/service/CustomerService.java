package com.Banking.BankingApi.service;

import com.Banking.BankingApi.domain.*;
import com.Banking.BankingApi.model.BankAccount;
import com.Banking.BankingApi.model.Credit;
import com.Banking.BankingApi.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface CustomerService {

    // Guarda un cliente en el repositorio
    Mono<CustomerDomain> save(Mono<CustomerDomain> customer);

    // Encuentra un cliente por su ID
    Mono<CustomerDomain> findById(String id);

    // Encuentra un cliente por su nombre
    Mono<CustomerDomain> findByNombre(String nombre);

    // Actualiza la información de un cliente
    Mono<CustomerDomain> update(String id, Mono<CustomerDomain> customer);

    // Elimina un cliente del repositorio
    Mono<Void> delete(CustomerDomain customer);

    // Obtiene una lista de todos los clientes
    Flux<CustomerDomain> findAll();

    // Encuentra cuentas asociadas a un cliente por su ID
    Mono<CustomerDomain> findAccountsByCustomerId(String customerId);

    // Añade una nueva cuenta bancaria a un cliente
    Mono<CustomerDomain> addAccountToCustomer(String customerId, BankAccount account);

    // Encuentra créditos asociados a un cliente por su ID
    Flux<CreditDomain> findCreditsByCustomerId(String customerId);

    // Añade un nuevo crédito a un cliente
    Mono<CreditDomain> addCreditToCustomer(String customerId, CreditDomain credit);

    // Encuentra transacciones por ID de cliente, cuenta bancaria y crédito
    Flux<TransactionDomain> findTransactionsByCustomerIdAndAccountIdAndLoanId(String customerId, String accountId, String loanId);


    // Añadir una cuenta bancaria a un cliente
    Mono<BankAccountDomain> addAccountToCustomer(String customerId, BankAccountDomain bankAccount);

    Mono<TransactionDomain> processTransaction(String accountId, TransactionDomain transaction);

    Mono<CustomerSummaryDomain> getCustomerSummary(String customerId);

    Mono<Boolean> hasOverdueDebt(String customerId);

}
