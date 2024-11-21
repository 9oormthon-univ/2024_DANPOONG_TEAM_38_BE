package boosters.fundboost.like.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class LikeException extends GeneralException {
    public LikeException(BaseErrorCode code) {
        super(code);
    }
}
