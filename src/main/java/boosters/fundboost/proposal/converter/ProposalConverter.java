package boosters.fundboost.proposal.converter;

import boosters.fundboost.global.utils.PeriodUtil;
import boosters.fundboost.proposal.domain.Proposal;
import boosters.fundboost.proposal.dto.response.ProposalResponse;
import org.springframework.data.domain.Page;

public class ProposalConverter {
    public static Page<ProposalResponse> toProposalPreviewResponse(Page<Proposal> proposals) {
        return proposals.map(proposal -> ProposalResponse.builder()
                .userName(proposal.getUser().getName())
                .userName(proposal.getUser().getImage())
                .title(proposal.getTitle())
                .content(proposal.getContent())
                .createdAt(PeriodUtil.localDateTimeToPeriodFormat(proposal.getCreatedAt()))
                .build());
    }
}
