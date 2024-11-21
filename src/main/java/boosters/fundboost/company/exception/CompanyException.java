package boosters.fundboost.company.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class CompanyException extends GeneralException {
    public CompanyException(BaseErrorCode code) {
        super(code);
    }
}
