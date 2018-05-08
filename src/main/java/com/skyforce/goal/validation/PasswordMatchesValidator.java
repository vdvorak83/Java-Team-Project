package com.skyforce.goal.validation;

import com.skyforce.goal.form.UserRegistrationForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches passwordMatches) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserRegistrationForm form = (UserRegistrationForm) object;

        return form.getPassword().equals(form.getMatchingPassword());
    }
}
