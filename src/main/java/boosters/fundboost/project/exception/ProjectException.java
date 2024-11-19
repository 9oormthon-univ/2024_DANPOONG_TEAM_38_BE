package boosters.fundboost.project.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class ProjectException extends GeneralException {
    public ProjectException(BaseErrorCode code) {
        super(code);
    }
}
