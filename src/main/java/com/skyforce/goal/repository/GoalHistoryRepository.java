package com.skyforce.goal.repository;

import com.skyforce.goal.model.GoalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalHistoryRepository extends JpaRepository<GoalHistory, Long> {
}
