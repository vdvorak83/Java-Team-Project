package com.skyforce.goal.controller;

import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.UserFollowingRepository;
import com.skyforce.goal.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SubscriptionController {
    @Autowired
   private AuthenticationService authenticationService;

    @Autowired
   private UserFollowingRepository followingRepository;

    @GetMapping("user/subscriptions")
    public String getSubscriptionPage(Authentication authentication,Model model){
        User user = authenticationService.getUserByAuthentication(authentication);
        model.addAttribute("user", user);
        model.addAttribute("subs",followingRepository.findByUserId(user.getId()));

        return "subscriptions";
    }
}
