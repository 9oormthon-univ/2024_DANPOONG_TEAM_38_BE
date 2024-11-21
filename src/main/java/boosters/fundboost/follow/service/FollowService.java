package boosters.fundboost.follow.service;

import boosters.fundboost.user.domain.User;

public interface FollowService {
    long getFollowingCount(User user);

    long getFollowerCount(User user);

    void followUser(User user, long followUserId);

    void unfollowUser(User user, long unFollowedUser);
}
