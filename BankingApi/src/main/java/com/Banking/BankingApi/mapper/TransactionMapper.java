package com.Banking.BankingApi.mapper;


import com.Banking.BankingApi.domain.CustomerDomain;
import com.Banking.BankingApi.domain.TransactionDomain;
import com.Banking.BankingApi.model.Customer;
import com.Banking.BankingApi.model.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
// Clase mapper para convertir entre entidades Transaction y TransactionDomain
public class TransactionMapper implements EntityMapper<Transaction, TransactionDomain>{
    @Override
    public TransactionDomain toDomain(Transaction model) {
        TransactionDomain transactionDomain =  new TransactionDomain();
        // Copia las propiedades del modelo a la entidad de dominio
        BeanUtils.copyProperties(model, transactionDomain);
        return transactionDomain; // Devuelve la entidad de dominio convertida
    }

    @Override
    public Transaction toModel(TransactionDomain domain) {
        Transaction Transaction = new Transaction();
        // Copia las propiedades de la entidad de dominio al modelo
        BeanUtils.copyProperties(domain, Transaction);
        return Transaction; // Devuelve el modelo convertido
    }
}
