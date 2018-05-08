package com.skyforce.goal.controller;

import com.skyforce.goal.dto.GoalDto;
import com.skyforce.goal.model.Goal;
import com.skyforce.goal.model.User;
import com.skyforce.goal.service.AuthenticationService;
import com.skyforce.goal.service.FollowingService;
import com.skyforce.goal.service.GoalService;
import com.skyforce.goal.service.UserService;
import com.skyforce.goal.validator.GoalFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProfileController {
    private final AuthenticationService authenticationService;
    private final GoalService goalService;
    private final FollowingService followingService;
    private final UserService userService;

    private final GoalFormValidator goalFormValidator;

    @Autowired
    public ProfileController(AuthenticationService authenticationService, GoalService goalService, FollowingService followingService, UserService userService, GoalFormValidator goalFormValidator) {
        this.authenticationService = authenticationService;
        this.goalService = goalService;
        this.followingService = followingService;
        this.userService = userService;
        this.goalFormValidator = goalFormValidator;
    }

    @GetMapping("/user/profile")
    public String getProfilePage(Authentication authentication, Model model) {
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("goalForm", new GoalDto());
        model.addAttribute("goals", goalService.findGoalsByUser(authenticationService.getUserByAuthentication(authentication)));

        return "profile";
    }

    @PostMapping("/user/profile")
    public String createGoal(Authentication authentication, @ModelAttribute("goalForm") @Valid GoalDto goalDto, Model model,
                                 RedirectAttributes redirectAttributes) {
        User user = authenticationService.getUserByAuthentication(authentication);
        if (goalDto.getPrice().compareTo(user.getMoney()) > 0) {
            redirectAttributes.addFlashAttribute("error", "Not enough money");
        } else {
            Goal goal = goalService.createGoal(goalDto, authentication);
            redirectAttributes.addFlashAttribute("error", "Not enough money");
        }
        model.addAttribute("goals", goalService.findGoalsByUser(authenticationService.getUserByAuthentication(authentication)));
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        return "redirect:/user/profile";
    }

    @GetMapping("/user/{login}")
    public String getUserPage(Authentication authentication, Model model, @PathVariable("login") String login) {
        if (authenticationService.getUserByAuthentication(authentication) == userService.findUserByLogin(login))
            return "redirect:/user/profile";
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("thisUser", userService.findUserByLogin(login));

        return "public-profile";
    }

    @PostMapping("/user/{login}")
    public String followCurrentUser(Authentication authentication, Model model, @PathVariable("login") String login) {
        followingService.follow(authenticationService.getUserByAuthentication(authentication), userService.findUserByLogin(login));
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));

        return "redirect:/user/" + login;
    }

    @InitBinder("goalForm")
    public void initGoalFormValidator(WebDataBinder binder) {
        binder.addValidators(goalFormValidator);
    }
}
