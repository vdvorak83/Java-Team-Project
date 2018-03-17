package com.skyforce.goal.controller;

import com.skyforce.goal.form.UserRegistrationForm;
import com.skyforce.goal.service.RegistrationService;
import com.skyforce.goal.validator.UserRegistrationFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRegistrationFormValidator userRegistrationFormValidator;

    @InitBinder("registrationForm")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(userRegistrationFormValidator);
    }

    @GetMapping("/register")
    public String register(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/";
        }

        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationForm")UserRegistrationForm userRegistrationForm,
                           BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", errors.getAllErrors().get(0).getDefaultMessage());

            return "redirect:/register";
        }

        registrationService.register(userRegistrationForm);

        return "registration";
    }

    @GetMapping("/confirm/{uuid}")
    public String confirm(@PathVariable("uuid") String uuid) {
        registrationService.confirm(uuid);

        return "login";
    }
}
