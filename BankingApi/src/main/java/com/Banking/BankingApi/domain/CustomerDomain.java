package com.Banking.BankingApi.domain;

import com.Banking.BankingApi.model.BankProduct;
import com.Banking.BankingApi.model.Credit;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Getter
@Setter
@Document(collation = "customers")
public class CustomerDomain {
    @Id
    private String id;
    private String type;
    private String name;
    private List<BankProduct> productosBancarios;
    private List<Credit> productosCredito;
    private boolean hasCreditCard;
}
