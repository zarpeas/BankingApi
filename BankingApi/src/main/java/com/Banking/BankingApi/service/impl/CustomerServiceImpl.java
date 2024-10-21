package com.Banking.BankingApi.service.impl;

import com.Banking.BankingApi.domain.*;
import com.Banking.BankingApi.model.BankAccount;
import com.Banking.BankingApi.model.Credit;
import com.Banking.BankingApi.model.Transaction;
import com.Banking.BankingApi.repository.CreditRepository;
import com.Banking.BankingApi.repository.CustomerRepository;
import com.Banking.BankingApi.repository.BankAccountRepository;
import com.Banking.BankingApi.repository.TransactionRepository;
import com.Banking.BankingApi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;  // Repositorio para acceder a los datos de clientes

    @Autowired
    private BankAccountRepository bankAccountRepository;  // Repositorio para acceder a los datos de cuentas bancarias

    @Autowired
    private CreditRepository creditRepository;  // Repositorio para acceder a los datos de créditos

    @Autowired
    private TransactionRepository transactionRepository;  // Repositorio para acceder a los datos de transacciones

    // Guarda un cliente en el repositorio
    @Override
    public Mono<CustomerDomain> save(Mono<CustomerDomain> customer) {
        return customer.flatMap(customerRepository::insert);
    }

    // Encuentra un cliente por su ID
    @Override
    public Mono<CustomerDomain> findById(String id) {
        return customerRepository.findById(id);
    }

    // Encuentra un cliente por su nombre
    @Override
    public Mono<CustomerDomain> findByNombre(String nombre) {
        return customerRepository.findByCustomer(nombre);
    }

    // Actualiza la información de un cliente
    @Override
    public Mono<CustomerDomain> update(String id, Mono<CustomerDomain> customer) {
        return customerRepository.findById(id)
                .flatMap(c -> customer)
                .doOnNext(e -> e.setId(id))
                .flatMap(customerRepository::save);
    }

    // Elimina un cliente del repositorio
    @Override
    public Mono<Void> delete(CustomerDomain customer) {
        return customerRepository.delete(customer);
    }

    // Obtiene una lista de todos los clientes
    @Override
    public Flux<CustomerDomain> findAll() {
        return customerRepository.findAll();
    }

    // Encuentra cuentas asociadas a un cliente por su ID
    @Override
    public Mono<CustomerDomain> findAccountsByCustomerId(String customerId) {
        return customerRepository.findByCustomer(customerId);
    }

    // Añade una nueva cuenta bancaria a un cliente
    @Override
    public Mono<CustomerDomain> addAccountToCustomer(String customerId, BankAccount bankAccount) {
        return customerRepository.findById(customerId)
                .flatMap(customer -> {
                    bankAccount.setHolder(customer.getName());  // Asigna el nombre del cliente como titular de la cuenta
                    return bankAccountRepository.save(bankAccount);
                });
    }

    // Encuentra créditos asociados a un cliente por su ID
    @Override
    public Flux<CreditDomain> findCreditsByCustomerId(String customerId) {
        return creditRepository.findByCredit(customerId);
    }

    // Añade un nuevo crédito a un cliente
    @Override
    public Mono<CreditDomain> addCreditToCustomer(String customerId, CreditDomain credit) {
        return customerRepository.findById(customerId)
                .flatMap(customer -> {
                    credit.setHolder(customer.getName());  // Asigna el nombre del cliente como titular del crédito
                    return creditRepository.save(credit);
                });
    }

    // Encuentra transacciones por ID de cliente, cuenta bancaria y crédito
    @Override
    public Flux<TransactionDomain> findTransactionsByCustomerIdAndAccountIdAndLoanId(String customerId, String accountId, String loanId) {
        return transactionRepository.findByCustomerIdAndAccountIdAndLoanId(customerId, accountId, loanId);
    }
    // Añadir una cuenta bancaria a un cliente
    @Override
    public Mono<BankAccountDomain> addAccountToCustomer(String customerId, BankAccountDomain bankAccount) {
        return customerRepository.findById(customerId)
                .flatMap(customerDomain -> {
                    if (bankAccount.isVIP() && !customerDomain.isHasCreditCard()) {
                        return Mono.error(new IllegalStateException("Cliente VIP debe tener una tarjeta de crédito"));
                    }
                    if (bankAccount.isPYME() && !customerDomain.isHasCreditCard()) {
                        return Mono.error(new IllegalStateException("Cliente PYME debe tener una tarjeta de crédito"));
                    }
                    bankAccount.setHolder(customerDomain.getName());
                    return bankAccountRepository.save(bankAccount);
                });
    }
    // Procesar una transacción en una cuenta
    @Override
    public Mono<TransactionDomain> processTransaction(String accountId, TransactionDomain transaction) {
        return bankAccountRepository.findById(accountId)
                .flatMap(account -> {
                    if (account.getFreeTransactions() > 0) {
                        account.setFreeTransactions(account.getFreeTransactions() - 1);
                    } else {
                        // Aplicar comisión por transacción adicional
                        transaction.setFee(account.getTransactionFee());
                    }
                    account.getTransactions().add(transaction);
                    return bankAccountRepository.save(account).thenReturn(transaction);
                });



    }
    // Obtener un resumen consolidado de un cliente
    @Override
    public Mono<CustomerSummaryDomain> getCustomerSummary(String customerId) {
        return customerRepository.findById(customerId)
                .flatMap(customer -> {
                    Mono<List<BankAccountDomain>> accountsMono = bankAccountRepository.findByCustomerId(customerId).collectList();
                    Mono<List<Object>> creditsMono = creditRepository.findByCustomerId(customerId).collectList();
                    return Mono.zip(accountsMono, creditsMono)
                            .map(tuple -> new CustomerSummaryDomain(customer, tuple.getT1(), tuple.getT2()));
                });
    }

    // Verificar si un cliente tiene alguna deuda vencida
    @Override
    public Mono<Boolean> hasOverdueDebt(String customerId) {
        return creditRepository.findByCustomerId(customerId)
                .filter(credit -> credit.getStatus().equals("vencido"))
                .hasElements();
    }


}
