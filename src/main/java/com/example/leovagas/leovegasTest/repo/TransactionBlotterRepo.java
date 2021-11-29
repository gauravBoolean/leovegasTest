package com.example.leovagas.leovegasTest.repo;

import com.example.leovagas.leovegasTest.model.TransactionBlotter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionBlotterRepo extends JpaRepository<TransactionBlotter, Long> {

    List<TransactionBlotter> findByTransactionRef(String transactionRef);

    List<TransactionBlotter> findByUserId(Long userId);

    List<TransactionBlotter> findTop1ByUserIdOrderByTransactionDateDesc(Long userId);

}
