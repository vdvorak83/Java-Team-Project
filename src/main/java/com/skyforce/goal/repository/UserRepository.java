package com.skyforce.goal.repository;

import com.skyforce.goal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
