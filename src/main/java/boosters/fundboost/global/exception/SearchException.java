package boosters.fundboost.global.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class SearchException extends GeneralException {
    public SearchException(BaseErrorCode code) {
        super(code);
    }
}
