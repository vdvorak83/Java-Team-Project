package com.skyforce.goal.service;

import com.skyforce.goal.form.UserRegistrationForm;
import com.skyforce.goal.model.User;

public interface RegistrationService {
    User register(UserRegistrationForm userRegistrationForm);

    void confirm(String uuid);
}
