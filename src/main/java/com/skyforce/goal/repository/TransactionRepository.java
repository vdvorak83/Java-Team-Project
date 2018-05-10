package com.skyforce.goal.repository;

import com.skyforce.goal.model.Transaction;
import com.skyforce.goal.model.enums.TransactionState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByDate(Date date);

    List<Transaction> findAllByDateAndState(Date date, TransactionState state);

    Transaction findFirst1ByStateOrderByDateDesc(TransactionState state);
}
