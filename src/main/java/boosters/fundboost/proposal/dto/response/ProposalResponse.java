package boosters.fundboost.proposal.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProposalResponse {
    ProposalPreviewResponse proposal;
    List<String> files;
}
