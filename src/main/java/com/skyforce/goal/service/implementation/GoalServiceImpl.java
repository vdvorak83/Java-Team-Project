package com.skyforce.goal.service.implementation;

import com.skyforce.goal.dto.GoalDto;
import com.skyforce.goal.model.Goal;
import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.GoalRepository;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.security.state.GoalState;
import com.skyforce.goal.service.AuthenticationService;
import com.skyforce.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService{

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<Goal> findAll() {
        return goalRepository.findAll();
    }

    @Override
    public Goal createGoal(GoalDto goalDto, Authentication authentication) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Goal newGoal = Goal.builder()
                .user(authenticationService.getUserByAuthentication(authentication))
                .state(GoalState.INPROGRESS)
                .name(goalDto.getGoalName())
                .description(goalDto.getDescription())
                .dateStart(date)
                .dateEnd(goalDto.getDateEnd())
                .price(BigDecimal.valueOf(goalDto.getPrice()))
                .build();

        goalRepository.save(newGoal);

        return newGoal;
    }

    @Override
    public List<Goal> findGoalsByUser(User user) {
        return goalRepository.findGoalsByUser(user);
    }

    @Override
    public List<Goal> findAllByOrderByDateStartDesc() {
        return goalRepository.findAllByOrderByDateStartDesc();
    }
}
