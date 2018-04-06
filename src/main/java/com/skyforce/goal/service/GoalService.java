package com.skyforce.goal.service;

import com.skyforce.goal.dto.GoalDto;
import com.skyforce.goal.model.Goal;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface GoalService {
    List<Goal> findAll();

    Goal createGoal(GoalDto goalDto, Authentication authentication);
}
