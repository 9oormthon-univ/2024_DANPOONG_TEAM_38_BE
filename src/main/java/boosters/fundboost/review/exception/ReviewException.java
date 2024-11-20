package boosters.fundboost.review.exception;

import boosters.fundboost.global.response.code.status.ErrorStatus;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public ReviewException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}