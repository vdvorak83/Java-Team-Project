package com.skyforce.goal.service.implementation;

import com.skyforce.goal.model.Goal;
import com.skyforce.goal.repository.GoalRepository;
import com.skyforce.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    private GoalRepository goalRepository;

    @Override
    public List<Goal> findAll() {
        return goalRepository.findAll();
    }
}
