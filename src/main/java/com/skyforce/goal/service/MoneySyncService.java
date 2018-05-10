package com.skyforce.goal.service;

import com.skyforce.goal.model.Wallet;

public interface MoneySyncService {
    void updateTransactions();

    void resyncTransactions();

    void syncBalances();

    Wallet saveWallet(String address);
}
