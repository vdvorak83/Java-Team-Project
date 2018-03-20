package com.skyforce.goal.service;

import com.skyforce.goal.model.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    User getUserByAuthentication(Authentication authentication);
}
