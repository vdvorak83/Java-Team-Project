package com.skyforce.goal.service;

import com.skyforce.goal.model.User;

public interface UserService {
    User findUserByLogin(String login);
}
