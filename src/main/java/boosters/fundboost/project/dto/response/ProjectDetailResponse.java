package boosters.fundboost.project.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectDetailResponse {
    private Long id;
    private String mainTitle;
    private String subTitle;
    private List<String> images;
    private String introduction;
    private String budgetDescription;
    private String scheduleDescription;
    private String teamDescription;
    private String summary;
}
