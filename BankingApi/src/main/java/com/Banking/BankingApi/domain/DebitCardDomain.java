package com.Banking.BankingApi.domain;

import com.Banking.BankingApi.model.Transaction;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Data
@Document(collection = "debitCards")
public class DebitCardDomain {
    @Id
    private String id;
    private String customerId;
    private String primaryAccountId;
    private List<String> linkedAccountIds;
    private List<Transaction> transactions;
}
