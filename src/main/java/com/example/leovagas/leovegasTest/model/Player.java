package com.example.leovagas.leovegasTest.model;


import lombok.Data;

@Data
public class  Player{
    int userId;
    Double amount;

    public Player(int userId, double amount){
        this.amount = amount;
        this.userId = userId;
    }

}
