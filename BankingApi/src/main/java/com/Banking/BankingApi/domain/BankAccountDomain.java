package com.Banking.BankingApi.domain;


import com.Banking.BankingApi.model.Transaction;
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
public class BankAccountDomain {
    @Id
    private String id;
    private String type;
    private double balance;
    private String holder;
    private List<String> authorizedSigners;
    private List<Transaction> transactions;
    private boolean isVIP;
    private boolean isPYME;
    private int freeTransactions;
    private double transactionFee;


}
