package com.Banking.BankingApi.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
@Document(collation = "PaymentTransactionDomain")
public class PaymentTransactionDomain {
    @Id
    private String id;
    private String fromPhoneNumber;
    private String toPhoneNumber;
    private double amount;
    private LocalDateTime timestamp;
    private String status;


}
