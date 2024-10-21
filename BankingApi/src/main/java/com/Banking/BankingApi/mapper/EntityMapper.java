package com.Banking.BankingApi.mapper;

// Interfaz genérica para mapear entre los dominios y los modelos
public interface EntityMapper<D, E> {

    // Convierte un modelo (D) a un dominio (E)
    E toDomain(D model);

    // Convierte un dominio (E) a un modelo (D)
    D toModel(E domain);

}
