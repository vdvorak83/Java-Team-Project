package com.skyforce.goal.validator;

import com.skyforce.goal.dto.GoalDto;
import com.skyforce.goal.model.Goal;
import com.skyforce.goal.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class GoalFormValidator implements Validator {
    @Autowired
    private GoalRepository goalRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.getName().equals(GoalDto.class.getName());
    }

    @Override
    public void validate(Object o, Errors errors) {
        GoalDto goalDto = (GoalDto) o;
        Optional<Goal> goals = goalRepository.findGoalByName(goalDto.getGoalName());

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"goalName","empty.goalName",
                "Goal name can't be empty!");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"description","empty.description",
                "Description can't be empty!");
    }
}
