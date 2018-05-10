package com.skyforce.goal.service;

import com.skyforce.goal.model.User;

import java.math.BigDecimal;

public interface MoneyService {

    /**
     * Creates Bitcoin wallet for user. Wallet if stored in User's "wallet" field.
     *
     * @return true if operation was successful.
     */
    boolean createWallet(User user);

    boolean sendMoney(User user, String address, BigDecimal amount);

    BigDecimal estimateFee(String address, BigDecimal amount);
}
