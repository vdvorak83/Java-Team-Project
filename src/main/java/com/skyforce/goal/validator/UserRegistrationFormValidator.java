package com.skyforce.goal.validator;

import com.skyforce.goal.form.UserRegistrationForm;
import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserRegistrationFormValidator implements Validator {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.getName().equals(UserRegistrationForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationForm form = (UserRegistrationForm) target;
        Optional<User> user = userRepository.findUserByLogin(form.getLogin());

        if (user.isPresent()) {
            errors.reject("bad.login", "This login is already in use");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "login", "empty.login", "Empty login");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "password", "empty.password", "Empty password");
    }
}
