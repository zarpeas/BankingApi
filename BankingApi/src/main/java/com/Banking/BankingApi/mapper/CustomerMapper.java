package com.Banking.BankingApi.mapper;


import com.Banking.BankingApi.domain.CustomerDomain;
import com.Banking.BankingApi.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
// Clase mapper para convertir entre entidades Customer y CustomerDomain
public class CustomerMapper implements EntityMapper<Customer, CustomerDomain>{
    @Override
    public CustomerDomain toDomain(Customer model) {
        CustomerDomain customerDomain =  new CustomerDomain();
        // Copia las propiedades del modelo a la entidad de dominio
        BeanUtils.copyProperties(model, customerDomain);
        return customerDomain; // Devuelve la entidad de dominio convertida
    }

    @Override
    public Customer toModel(CustomerDomain domain) {
        Customer customer = new Customer();
        // Copia las propiedades de la entidad de dominio al modelo
        BeanUtils.copyProperties(domain, customer);
        return customer; // Devuelve el modelo convertido
    }
}
