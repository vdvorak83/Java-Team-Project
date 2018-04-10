package com.skyforce.goal.service.implementation;

import com.skyforce.goal.model.User;
import com.skyforce.goal.model.UserFollowing;
import com.skyforce.goal.repository.UserFollowingRepository;
import com.skyforce.goal.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class FollowingServiceImpl implements FollowingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserFollowingRepository userFollowingRepository;

    @Override
    @Transactional
    public void follow(User user, User following) {
        UserFollowing userFollowing = UserFollowing.builder()
                .user(user)
                .following(following)
                .build();

        if (userFollowingRepository.existsByUserAndFollowing(user, following))
            return;

        entityManager.merge(userFollowing);
    }

    @Override
    public void unfollow(User user, User following) {

        //entityManager.remove();
    }
}
