package boosters.fundboost.proposal.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProposalResponse {
    private String userName;
    private String image;
    private String title;
    private String content;
    private String createdAt;
}
