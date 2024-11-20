package boosters.fundboost.proposal.service;

import boosters.fundboost.proposal.dto.response.ProposalResponse;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.request.ProposalRequest;
import org.springframework.data.domain.Page;

public interface ProposalService {
    void writeProposal(User user, ProposalRequest request);

    Page<ProposalResponse> getProposals(User user, int page);

    ProposalResponse getProposal(Long id);
}
