package com.Banking.BankingApi.domain;
import com.Banking.BankingApi.model.Transaction;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Data
@Getter
@Setter
@Document(collation = "customers")
public class TransactionDomain extends Transaction {
    @Id
    private String id;
    private String type;
    private double amount;
    private Date date;
    private String accountId;
    private String creditId;
    private double fee;
}
