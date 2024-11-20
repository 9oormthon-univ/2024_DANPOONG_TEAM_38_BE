package boosters.fundboost.global.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class ValidationException extends GeneralException {
    public ValidationException(BaseErrorCode code) {
        super(code);
    }
}
