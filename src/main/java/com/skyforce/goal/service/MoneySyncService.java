package com.skyforce.goal.service;

import com.skyforce.goal.model.Transaction;
import com.skyforce.goal.model.Wallet;

import java.util.List;

public interface MoneySyncService {
    void updateTransactions();

    void resyncTransactions();

    void syncBalances();

    Wallet saveWallet(String address);

    void saveAll(List<Transaction> transactions);
}
