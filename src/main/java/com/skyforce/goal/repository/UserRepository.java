package com.skyforce.goal.repository;

import com.skyforce.goal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByUuid(String uuid);
}
