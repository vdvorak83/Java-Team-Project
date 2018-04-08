package com.skyforce.goal.service.implementation;

import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByLogin(String login) {
        if (userRepository.findUserByLogin(login).isPresent()) {
            return userRepository.findUserByLogin(login).get();
        } else {
            throw new UsernameNotFoundException("User with login " + login + " not found");
        }
    }
}
