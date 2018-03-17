package com.skyforce.goal.controller;

import com.skyforce.goal.model.User;
import com.skyforce.goal.security.role.UserRole;
import com.skyforce.goal.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication != null) {
            User user = authenticationService.getUserByAuthentication(authentication);

            if (user.getRole().equals(UserRole.ADMIN.getValue())) {
                return "redirect:/admin";
            } else if (user.getRole().equals(UserRole.USER.getValue())){
                return "redirect:/user/profile";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Authentication authentication) {
        if (authentication != null) {
            request.getSession().invalidate();
        }

        return "redirect:/login";
    }
}
