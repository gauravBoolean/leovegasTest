package com.example.leovagas.leovegasTest.controller;

import com.example.leovagas.leovegasTest.model.TransactionRequestPOJO;
import com.example.leovagas.leovegasTest.service.TransactionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class DebitCreditController {

    @Autowired
    private TransactionService service;

    @PostMapping("/debit")
    public ResponseEntity<?> debitAccount(@RequestBody TransactionRequestPOJO body){
        return ResponseEntity.ok(service.debitAmount(body));
    }

    @PostMapping("/credit")
    public ResponseEntity<?> creditAccount(@RequestBody TransactionRequestPOJO body){
        return ResponseEntity.ok(service.creditAmount(body));
    }

    @GetMapping("/player/{player_id}/transaction")
    public ResponseEntity<?> getPlayerTransactions(
            @PathVariable("player_id") long playerId
    ){
        //todo: make use of stream , map
        //todo: create new pojo for response
        return ResponseEntity.ok(service.fetchAllRecords(playerId));
    }

    @GetMapping("/player/{player_id}/balance")
    public ResponseEntity<?> getPlayerBalance(
            @PathVariable("player_id") long playerId
    ){
        return ResponseEntity.ok(service.getCurrentBalance(playerId));
    }


}
