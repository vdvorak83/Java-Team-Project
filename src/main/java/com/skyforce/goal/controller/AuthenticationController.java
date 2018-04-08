package com.skyforce.goal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthenticationController {
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
