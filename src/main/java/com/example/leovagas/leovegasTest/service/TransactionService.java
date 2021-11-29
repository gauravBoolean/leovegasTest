package com.example.leovagas.leovegasTest.service;

import com.example.leovagas.leovegasTest.model.TransactionBlotter;
import com.example.leovagas.leovegasTest.model.TransactionRequestPOJO;
import com.example.leovagas.leovegasTest.model.TransactionType;
import com.example.leovagas.leovegasTest.repo.TransactionBlotterRepo;
import com.example.leovagas.leovegasTest.validation.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;

@Service
public class TransactionService {

    @Autowired
    private TransactionBlotterRepo repo;

    @Transactional
    public TransactionBlotter debitAmount(TransactionRequestPOJO transactionReq){
        double calculateBalance = calculateBalance(transactionReq.getPlayerId(), transactionReq.getAmount(), TransactionType.DEBIT);
        if(calculateBalance < 0)
            throw  new InsufficientBalanceException("Insufficient Balance to make withdrawal");
        return repo.saveAndFlush(createDebitTransaction.apply(transactionReq, calculateBalance));
    }

    @Transactional
    public TransactionBlotter creditAmount(TransactionRequestPOJO transactionReq){
        double calculateBalance = calculateBalance(transactionReq.getPlayerId(), transactionReq.getAmount(), TransactionType.CREDIT);
        return repo.saveAndFlush(createCreditTransaction.apply(transactionReq, calculateBalance));
    }


    public List<TransactionBlotter> fetchAllRecords(Long playerId){
        return repo.findByUserId(playerId);
    }

    public List<TransactionBlotter> fetchTransactionFromRef(String transactionRef){
        return repo.findByTransactionRef(transactionRef);
    }

    BiFunction<TransactionRequestPOJO, Double ,TransactionBlotter> createDebitTransaction = (transactionRequestPOJO, balance) -> {
        TransactionBlotter blotter = new TransactionBlotter();
        blotter.setUserId(transactionRequestPOJO.getPlayerId());
        blotter.setTransactionDate(new Date());
        blotter.setTransactionType(TransactionType.DEBIT);
        blotter.setAmount(transactionRequestPOJO.getAmount());
        blotter.setTransactionRef(transactionRequestPOJO.getTransactionRef());
        blotter.setBalance(balance);
        return blotter;
    };

    BiFunction<TransactionRequestPOJO, Double ,TransactionBlotter> createCreditTransaction =   (transactionRequestPOJO, balance) -> {
        TransactionBlotter blotter = new TransactionBlotter();
        blotter.setUserId(transactionRequestPOJO.getPlayerId());
        blotter.setTransactionDate(new Date());
        blotter.setTransactionType(TransactionType.CREDIT);
        blotter.setAmount(transactionRequestPOJO.getAmount());
        blotter.setTransactionRef(transactionRequestPOJO.getTransactionRef());
        blotter.setTransactionRef(transactionRequestPOJO.getTransactionRef());
        return blotter;
    };


    private boolean isValidTransactionRef(TransactionRequestPOJO transactionReq){
        List<TransactionBlotter> list = fetchTransactionFromRef(transactionReq.getTransactionRef());
        if(Objects.isNull(list) || list.isEmpty())
            return true;
        return false;
    }

    public double getCurrentBalance(long playerId){
        Double balance = currentPlayerBalance(playerId);
        return Objects.isNull(balance) ? 0.0 : balance;
    }

    private Double currentPlayerBalance(long playerId) {
        List<TransactionBlotter> list = repo.findTop1ByUserIdOrderByTransactionDateDesc(playerId);
        Optional<TransactionBlotter> tb = Optional.ofNullable(list).orElse(Collections.emptyList()).stream().findFirst();
        if(tb.isPresent())
            return tb.get().getBalance();
        else
            return null;
    }

    private double calculateBalance(long playerId, double amount, TransactionType transactionType){
        Double balance = currentPlayerBalance(playerId);
        double currentBalance = Objects.isNull(balance) ? 0.0 : balance;;
        amount = transactionType.equals(TransactionType.DEBIT) ? amount * -1 : amount;
        return currentBalance + amount;
    }

}
