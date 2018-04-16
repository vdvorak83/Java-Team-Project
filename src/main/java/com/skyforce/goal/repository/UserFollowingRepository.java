package com.skyforce.goal.repository;

import com.skyforce.goal.model.User;
import com.skyforce.goal.model.UserFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserFollowingRepository extends JpaRepository<UserFollowing, Long> {
    boolean existsByUserAndFollowing(User user, User following);

    UserFollowing findByUserAndFollowing(User user, User following);

    List<UserFollowing> findByUserId(Integer id);

    List<UserFollowing> findByFollowingId(Integer id);
}
