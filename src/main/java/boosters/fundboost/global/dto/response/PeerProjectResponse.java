package boosters.fundboost.global.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeerProjectResponse {
    private Long id;
    private String mainTitle;
    private String introduction;
    private String image;
    private double progressRate;
    private long achievedAmount;
    private String progressPeriod;
    private String targetAmount;
}
