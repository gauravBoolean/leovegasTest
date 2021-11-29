package com.example.leovagas.leovegasTest.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction_blotter")
public class TransactionBlotter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userId;

    private TransactionType transactionType;

    private double amount;

    private Date transactionDate;

    private String transactionRef;

    private double balance;

}
