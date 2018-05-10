package com.skyforce.goal.controller;

import com.skyforce.goal.model.User;
import com.skyforce.goal.security.role.UserRole;
import com.skyforce.goal.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication != null) {
            User user = authenticationService.getUserByAuthentication(authentication);

            if (user.getRole().equals(UserRole.ADMIN)) {
                return "redirect:/admin";
            } else if (user.getRole().equals(UserRole.USER)) {
                return "redirect:/user/profile";
            }
        }

        return "homepage";
    }
}
