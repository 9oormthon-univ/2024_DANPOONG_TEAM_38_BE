package boosters.fundboost.project.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoostedProjectResponse {
    private Long id;
    private String mainTitle;
    private String introduction;
    private String image;
    private double progressRate; // 기여도
    private String boostedAmount; // 후원금액
    private String progressPeriod;
    private long daysLeft;
}
