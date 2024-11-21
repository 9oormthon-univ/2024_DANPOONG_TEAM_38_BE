package boosters.fundboost.proposal.exception;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.exception.GeneralException;

public class ProposalException extends GeneralException {
    public ProposalException(BaseErrorCode code) {
        super(code);
    }
}
