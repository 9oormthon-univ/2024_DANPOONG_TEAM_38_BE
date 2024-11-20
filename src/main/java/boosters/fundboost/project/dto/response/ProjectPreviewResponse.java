package boosters.fundboost.project.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectPreviewResponse {
    private String image;
    private String mainTitle;
    private String period;
    private String progress;
    private String category;
    private long daysLeft;
}
