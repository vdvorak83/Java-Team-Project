package com.skyforce.goal.repository;

import com.skyforce.goal.model.Goal;
import com.skyforce.goal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findGoalByName(String name);

    List<Goal> findGoalsByUser(User user);
}
