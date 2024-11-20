package boosters.fundboost.proposal.service;

import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.request.ProposalRequest;

public interface ProposalService {
    void writeProposal(User user, ProposalRequest request);
}
