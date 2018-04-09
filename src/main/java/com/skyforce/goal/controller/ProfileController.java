package com.skyforce.goal.controller;

import com.skyforce.goal.dto.GoalDto;
import com.skyforce.goal.model.Goal;
import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.service.AuthenticationService;
import com.skyforce.goal.service.GoalService;
import com.skyforce.goal.service.ImageService;
//import com.skyforce.goal.validator.GoalFormValidator;
import com.skyforce.goal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private UserService userService;

    /*@Autowired
    private GoalFormValidator goalFormValidator;
*/
//    @InitBinder("goalForm")
//    public void initGoalFormValidator(WebDataBinder binder) {
//        binder.addValidators(goalFormValidator);
//    }

    @GetMapping("/user/profile")
    public String getProfilePage(Authentication authentication, Model model) {
//        model.addAttribute("login", authenticationService.getUserByAuthentication(authentication).getLogin());
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("goalForm",new GoalDto());
        model.addAttribute("goals", goalService.findGoalsByUser(authenticationService.getUserByAuthentication(authentication)));

        return "profile";
    }

    @PostMapping("/user/profile")
    public String getGoalCreated(Authentication authentication, @ModelAttribute("goalForm") GoalDto goalDto, Model model){
        Goal goal = goalService.createGoal(goalDto, authentication);
        model.addAttribute("goal", goal);

        return "redirect:/goals";
    }

    @GetMapping("/user/{login}")
    public String getUserPage(Authentication authentication, Model model, @PathVariable("login") String login) {
        if (authenticationService.getUserByAuthentication(authentication) == userService.findUserByLogin(login))
            return "redirect:/user/profile";
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("thisUser", userService.findUserByLogin(login));

        return "publicProfile";
    }

}
