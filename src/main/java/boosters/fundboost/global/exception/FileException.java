package boosters.fundboost.global.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class FileException extends GeneralException {
    public FileException(BaseErrorCode code) {
        super(code);
    }
}
