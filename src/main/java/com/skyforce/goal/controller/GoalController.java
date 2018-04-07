package com.skyforce.goal.controller;

import com.skyforce.goal.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoalController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("user/goal")
    public String getGoalPage(Authentication authentication, Model model){
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));

        return "goal";
    }
}
