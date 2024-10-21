package com.Banking.BankingApi.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collation = "credits")
public class CreditDomain {

        @Id
        private String id;
        private String type;
        private double amount;
        private double outstandingBalance;
        private double creditLimit;
        private String status;
        private String holder;
}
