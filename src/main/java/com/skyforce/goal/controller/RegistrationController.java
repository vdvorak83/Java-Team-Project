package com.skyforce.goal.controller;

import com.skyforce.goal.dto.UserDto;
import com.skyforce.goal.model.User;
import com.skyforce.goal.service.RegistrationService;
import com.skyforce.goal.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    private UserDto userDto;

    /*@Autowired
    private UserRegistrationFormValidator userRegistrationFormValidator;*/

    /*@InitBinder("registrationForm")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(userRegistrationFormValidator);
    }*/

    @GetMapping("/register")
    public String register(Model model, Authentication authentication) {
        if (authentication != null) {
            return "redirect:/";
        }

        UserDto userDto = new UserDto();

        model.addAttribute("user", userDto);

        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid UserDto userDto, BindingResult errors,
                           RedirectAttributes redirectAttributes) {
        User registered;

        if (errors.hasErrors()) {
            return "registration";
        } else {
            try {
                registered = registrationService.register(userDto);
            } catch (EmailExistsException e) {
                return "registration";
            }
        }

        if (registered == null) {
            errors.rejectValue("email", "message.regError");
        }

        return "registration";
    }

    @GetMapping("/confirm/{uuid}")
    public String confirm(@PathVariable("uuid") String uuid) {
        registrationService.confirm(uuid);

        return "login";
    }

    /*@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto,
             BindingResult result, WebRequest request, Errors errors) {
        User registered = new User();

        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        // rest of the implementation
    }

    private User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered = null;
        try {
            registered = service.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }*/
}
