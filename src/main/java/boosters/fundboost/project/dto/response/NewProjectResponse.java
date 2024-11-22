package boosters.fundboost.project.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewProjectResponse {
    private Long id;
    private String mainTitle;
    private String image;
    private String category;
    private String region;
    private double progressRate;
    private String achievedAmount;
    private String progressPeriod;
    private boolean isCorporateFunding;
    private String userName;
}
