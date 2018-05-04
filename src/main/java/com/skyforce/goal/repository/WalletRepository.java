package com.skyforce.goal.repository;

import com.skyforce.goal.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findWalletByAddress(String address);
}
