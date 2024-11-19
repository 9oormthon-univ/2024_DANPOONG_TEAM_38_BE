package boosters.fundboost.user.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(BaseErrorCode code) {
        super(code);
    }
}
