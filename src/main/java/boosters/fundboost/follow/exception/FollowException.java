package boosters.fundboost.follow.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class FollowException extends GeneralException {

    public FollowException(BaseErrorCode code) {
        super(code);
    }
}
