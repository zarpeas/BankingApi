package com.Banking.BankingApi.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Getter
@Setter
@Document(collation = "Wallet")
public class WalletDomain {

    @Id
    private String id;
    private String documentId;
    private String phoneNumber;
    private String imei;
    private String email;
    private String linkedDebitCardId;
    private double balance;
    private List<PaymentTransactionDomain> transaction;
}
