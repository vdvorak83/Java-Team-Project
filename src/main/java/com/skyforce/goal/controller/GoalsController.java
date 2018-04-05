package com.skyforce.goal.controller;

import com.skyforce.goal.service.AuthenticationService;
import com.skyforce.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GoalsController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GoalService goalService;

    @GetMapping("/goals")
    public String getGoalsPage(Authentication authentication, Model model) {
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("goals", goalService.findAll());

        return "goals";
    }

    @GetMapping("/goal/{id}")
    public String getGoalPage(Authentication authentication, Model model, @PathVariable("id") Long id) {
        return "goal";
    }
}
