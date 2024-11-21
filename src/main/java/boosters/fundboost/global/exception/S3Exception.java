package boosters.fundboost.global.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class S3Exception extends GeneralException {
    public S3Exception(BaseErrorCode code) {
        super(code);
    }
}
