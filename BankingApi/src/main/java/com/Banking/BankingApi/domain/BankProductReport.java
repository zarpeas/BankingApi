package com.Banking.BankingApi.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
@Document(collation = "BankProductReport")
public class BankProductReport {
    private String productId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
