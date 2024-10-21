package com.Banking.BankingApi.mapper;


import com.Banking.BankingApi.domain.CreditDomain;
import com.Banking.BankingApi.model.Credit;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
// Clase mapper para convertir entre entidades Credit y CreditDomain
public class CreditMapper implements EntityMapper<Credit, CreditDomain>{
    @Override
    public CreditDomain toDomain(Credit model) {
        CreditDomain CreditDomain =  new CreditDomain();
        // Copia las propiedades del modelo a la entidad de dominio
        BeanUtils.copyProperties(model, CreditDomain);
        return CreditDomain; // Devuelve la entidad de dominio convertida
    }

    @Override
    public Credit toModel(CreditDomain domain) {
        Credit Credit = new Credit();
        // Copia las propiedades de la entidad de dominio al modelo
        BeanUtils.copyProperties(domain, Credit);
        return Credit; // Devuelve el modelo convertido
    }
}
