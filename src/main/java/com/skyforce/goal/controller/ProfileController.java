package com.skyforce.goal.controller;

import com.skyforce.goal.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProfileController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/user/profile")
    public String getProfilePage(Authentication authentication, @ModelAttribute("model") ModelMap model) {
        //model.addAttribute("name", authenticationService.getUserByAuthentication(authentication).getLogin());
        model.put("name", authenticationService.getUserByAuthentication(authentication).getLogin());

        System.out.println(authenticationService.getUserByAuthentication(authentication).getLogin());

        return "profile";
    }
}
