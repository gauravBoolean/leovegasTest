package com.example.leovagas.leovegasTest.model;

import lombok.Data;

@Data
public class TransactionRequestPOJO {

    private String transactionRef;

    private long playerId;

    private double amount;


}
