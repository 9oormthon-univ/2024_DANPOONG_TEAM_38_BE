package boosters.fundboost.global.utils;

import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.user.exception.UserException;

public class MyPageValidator {
    public static void validatePeerId(Long peerId, Long userId) {
        if (peerId.equals(userId)) {
            throw new UserException(ErrorStatus.SELF_PROFILE_ACCESS_NOT_ALLOWED);
        }
    }
}

