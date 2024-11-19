package boosters.fundboost.company.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyRankingResponse {
    private Long id;
    private String name;
    private String image;
    private String category;
    private long contributionAmount;
    private long contributionCount;
}
