package com.Banking.BankingApi.mapper;

import com.Banking.BankingApi.domain.BankAccountDomain;
import com.Banking.BankingApi.model.BankAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
// Clase mapper para convertir entre entidades BankAccount y BankAccountDomain
public class AccountMapper implements EntityMapper<BankAccount, BankAccountDomain> {

    // Método para convertir un modelo de BankAccount a un dominio de BankAccountDomain
    @Override
    public BankAccountDomain toDomain(BankAccount model) {
        BankAccountDomain accountDomain = new BankAccountDomain();
        // Copia las propiedades del modelo a la entidad de dominio
        BeanUtils.copyProperties(model, accountDomain);
        return accountDomain; // Devuelve la entidad de dominio convertida
    }

    // Método para convertir un dominio de BankAccountDomain a un modelo de BankAccount
    @Override
    public BankAccount toModel(BankAccountDomain domain) {
        BankAccount bankAccount = new BankAccount();
        // Copia las propiedades de la entidad de dominio al modelo
        BeanUtils.copyProperties(domain, bankAccount);
        return bankAccount; // Devuelve el modelo convertido
    }
}
