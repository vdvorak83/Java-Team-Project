package com.skyforce.goal.repository;

import com.skyforce.goal.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findGoalByName(String name);
}
