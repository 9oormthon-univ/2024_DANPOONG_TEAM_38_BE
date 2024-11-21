package boosters.fundboost.global.utils;

import boosters.fundboost.follow.exception.FollowException;
import boosters.fundboost.global.response.code.status.ErrorStatus;

public class UserValidator {
    public static void validateFollow(long followerId, long followingId) {
        if (followerId == followingId) {
            throw new FollowException(ErrorStatus.FOLLOW_YOURSELF);
        }
    }
}
