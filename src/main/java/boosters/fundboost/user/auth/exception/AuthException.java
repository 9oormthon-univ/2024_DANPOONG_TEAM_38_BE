package boosters.fundboost.user.auth.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(BaseErrorCode code) {
        super(code);
    }
}
