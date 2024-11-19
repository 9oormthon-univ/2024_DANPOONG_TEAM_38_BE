package boosters.fundboost.global.redis.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class TokenException extends GeneralException {
    public TokenException(BaseErrorCode code) {
        super(code);
    }
}
