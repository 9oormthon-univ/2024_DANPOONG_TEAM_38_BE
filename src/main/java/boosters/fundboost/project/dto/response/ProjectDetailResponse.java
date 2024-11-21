package boosters.fundboost.project.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectDetailResponse {
    private Long id;
    private String mainTitle;
    private String subTitle;
    private String image;
    private String introduction;
    private String budgetDescription;
    private String scheduleDescription;
    private String teamDescription;
    private String summary;
}
