package com.example.leovagas.leovegasTest.controller;

import com.example.leovagas.leovegasTest.model.TransactionRequestPOJO;
import com.example.leovagas.leovegasTest.service.TransactionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

public class DebitCreditControllerTest {


    DebitCreditController debitCreditController;

    @Mock
    TransactionService transactionService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        debitCreditController = new DebitCreditController();
        debitCreditController.setService(transactionService);
    }
    @Test
    public void testFirst(){
        debitCreditController.creditAccount(getDummyPojo());
    }

    private TransactionRequestPOJO getDummyPojo(){
        TransactionRequestPOJO t = new TransactionRequestPOJO();
        t.setAmount(100.0);
        t.setPlayerId(1);
        t.setTransactionRef("aboc");

        return t;

    }
}
