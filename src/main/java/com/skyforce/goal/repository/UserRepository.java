package com.skyforce.goal.repository;

import com.skyforce.goal.model.Goal;
import com.skyforce.goal.model.User;
import com.skyforce.goal.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUuid(String uuid);

    User findUserByWallet(Wallet wallet);
}
