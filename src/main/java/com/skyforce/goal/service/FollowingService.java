package com.skyforce.goal.service;

import com.skyforce.goal.model.User;
import com.skyforce.goal.model.UserFollowing;
import org.dom4j.tree.AbstractEntity;

public interface FollowingService {
    void follow(User user, User following);

    void unfollow(User user, User following);
}
