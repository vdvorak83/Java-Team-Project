package com.skyforce.goal.service;

import com.skyforce.goal.form.UserRegistrationForm;
import com.skyforce.goal.model.User;
import com.skyforce.goal.exception.EmailExistsException;

public interface RegistrationService {
    User register(UserRegistrationForm userRegistrationForm) throws EmailExistsException;

    void confirm(String uuid);

    boolean emailExists(String email);
}
