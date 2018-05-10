package com.skyforce.goal.validator;

import com.skyforce.goal.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserRegistrationFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.getName().equals(UserRegistrationForm.class.getName());
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegistrationForm form = (UserRegistrationForm) o;

        if (!form.getPassword().equals(form.getMatchingPassword()))
            errors.reject("bad.password.match", "Passwords do not match");

        if(form.isCheckEmail())
            ValidationUtils.rejectIfEmpty(errors, "email", "empty.email", "Empty email");
    }
}
