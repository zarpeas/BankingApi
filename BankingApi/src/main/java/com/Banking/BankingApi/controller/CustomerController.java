package com.Banking.BankingApi.controller;
import com.Banking.BankingApi.api.CustomersApi;
import com.Banking.BankingApi.api.CustomersApiDelegate;
import com.Banking.BankingApi.domain.CreditDomain;
import com.Banking.BankingApi.mapper.CustomerMapper;
import com.Banking.BankingApi.model.BankAccount;
import com.Banking.BankingApi.model.Credit;
import com.Banking.BankingApi.model.Customer;
import com.Banking.BankingApi.model.Transaction;
import com.Banking.BankingApi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
// Controlador REST para manejar solicitudes relacionadas con los clientes
@RestController
public class CustomerController implements CustomersApi {

    private static final String TIMESTAMP = "timestamp";
    @Autowired
    private CustomerService customerService; // Inyección de dependencia del servicio de clientes

    @Autowired
    private CustomerMapper customerMapper; // Inyección de dependencia del mapper de clientes

    @Override
    public CustomersApiDelegate getDelegate() {
        return CustomersApi.super.getDelegate();
    }

    // Obtener las cuentas bancarias de un cliente por su ID
    @Override
    public Mono<ResponseEntity<Flux<BankAccount>>> customersCustomerIdAccountsGet(String customerId, ServerWebExchange exchange) {
        return customerService.findAccountsByCustomerId(customerId)
                .map(account -> ResponseEntity.ok(Flux.fromIterable(account)))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }
    // Crear una nueva cuenta bancaria para un cliente
    @Override
    public Mono<ResponseEntity<Void>> customersCustomerIdAccountsPost(String customerId, Mono<BankAccount> bankAccount, ServerWebExchange exchange) {
        return bankAccount.flatMap(account -> customerService.addAccountToCustomer(customerId, account))
                .map(account -> new ResponseEntity<Void>(HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }
    // Obtener los créditos de un cliente por su ID
    @Override
    public Mono<ResponseEntity<Flux<Credit>>> customersCustomerIdCreditsGet(String customerId, ServerWebExchange exchange) {
        return customerService.findCreditsByCustomerId(customerId)
                .map(credit -> ResponseEntity.ok(Flux.fromIterable(credit)) )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    // Crear un nuevo crédito para un cliente
    @Override
    public Mono<ResponseEntity<Void>> customersCustomerIdCreditsPost(String customerId, Mono<CreditDomain> credit, ServerWebExchange exchange) {
        return credit.flatMap(cr -> customerService.addCreditToCustomer(customerId, cr))
                .map(cr -> new ResponseEntity<Void>(HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    // Eliminar un cliente por su ID
    @Override
    public Mono<ResponseEntity<Void>> customersCustomerIdDelete(String customerId, ServerWebExchange exchange) {
        //return CustomersApi.super.customersCustomerIdDelete(customerId, exchange);
        return customerService.findById(customerId)
                .flatMap(c -> customerService.delete(c).
                        then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Obtener la información de un cliente por su ID
    @Override
    public Mono<ResponseEntity<Customer>> customersCustomerIdGet(String customerId, ServerWebExchange exchange) {
        //return CustomersApi.super.customersCustomerIdGet(customerId, exchange);
        return customerService.findById(customerId)
                .map(customerMapper::toModel).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    // Obtener las transacciones de un cliente
    @Override
    public Mono<ResponseEntity<Flux<Transaction>>> customersCustomerIdTransactionsGet(String customerId, String accountId, String loanId, ServerWebExchange exchange) {
        return CustomersApi.super.customersCustomerIdTransactionsGet(customerId, accountId, loanId, exchange);
    }
    // Obtener todos los clientes
    @Override
    public Mono<ResponseEntity<Flux<Customer>>> customersGet(ServerWebExchange exchange) {
        return CustomersApi.super.customersGet(exchange);
    }
    // Crear un nuevo cliente
    @Override
    public Mono<ResponseEntity<Map<String, Object>>> customersPost(Mono<Customer> customer, ServerWebExchange exchange) {
        //return CustomersApi.super.customersPost(customer, exchange);
        Map<String, Object> respose = new HashMap<>();
        return customerService.save(customer.map(customerMapper::toDomain))
                .map(customerMapper::toModel)
                .map(c->{
                    respose.put("customer", c);
                    respose.put("message", "Cliente creado con éxito");
                    respose.put(TIMESTAMP, new Date());
                    return ResponseEntity.status(HttpStatus.CREATED).body(respose);
                }).onErrorResume(getThrowableMonoFunction(respose));

    }
    // Manejar errores de vinculación de datos de solicitud web
    private static Function<Throwable, Mono<? extends ResponseEntity<Map<String, Object>>>> getThrowableMonoFunction(Map<String, Object> response){
        return  t -> Mono.just(t).cast(WebExchangeBindException.class)
                .flatMap(e-> Mono.just(e.getFieldErrors()))
                .flatMapMany(Flux::fromIterable)
                .map(fieldError -> "Campo" + fieldError.getField() +fieldError.getDefaultMessage())
                .collectList()
                .flatMap(l-> {
                    response.put(TIMESTAMP, new Date());
                    response.put("status", HttpStatus.BAD_REQUEST.value());
                    response.put("Error", l);
                    return  Mono.just(ResponseEntity.badRequest().body(response));
                });
    }

}
