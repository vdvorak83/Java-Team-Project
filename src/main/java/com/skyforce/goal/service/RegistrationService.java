package com.skyforce.goal.service;

import com.skyforce.goal.dto.UserDto;
import com.skyforce.goal.model.User;
import com.skyforce.goal.exception.EmailExistsException;

public interface RegistrationService {
    User register(UserDto userDto) throws EmailExistsException;

    void confirm(String uuid);

    boolean emailExists(String email);
}
