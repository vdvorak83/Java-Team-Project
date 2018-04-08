package com.skyforce.goal.controller;

import com.skyforce.goal.model.Goal;
import com.skyforce.goal.service.AuthenticationService;
import com.skyforce.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class GoalsController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GoalService goalService;

    @GetMapping("/goals")
    public String getGoalsPage(@RequestParam(value = "sort", required = false) String sort, Authentication authentication, Model model) {
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));

        if (sort == null) {
            model.addAttribute("goals", goalService.findAll());

            return "goals";
        }

        model.addAttribute("goals", goalService.findAllByOrderByDateStartDesc());

        return "goals";
    }

    /*public List getAllGoals(HttpServletRequest request, ModelMap model) {
        Goal goal = (Goal) request.getSession().getAttribute("goal");

        return
    }*/

    @GetMapping("/goal/{id}")
    public String getGoalPage(Authentication authentication, Model model, @PathVariable("id") Long id) {
        return "goal";
    }
}
