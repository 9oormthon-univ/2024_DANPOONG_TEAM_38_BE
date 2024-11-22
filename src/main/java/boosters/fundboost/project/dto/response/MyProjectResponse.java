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
public class MyProjectResponse {
    private Long id;
    private String mainTitle;
    private String image;
    private String category;
    private String progressStatus;
    private String progressPeriod;
    private boolean isFunding;
    private Integer daysRemaining;
}
