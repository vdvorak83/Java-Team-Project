package com.skyforce.goal.service.implementation;

import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.security.detail.UserDetailsImpl;
import com.skyforce.goal.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByAuthentication(Authentication authentication) {
        UserDetailsImpl currentUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        User currentUser = currentUserDetails.getUser();

        return userRepository.findUserById(currentUser.getId());
    }
}
