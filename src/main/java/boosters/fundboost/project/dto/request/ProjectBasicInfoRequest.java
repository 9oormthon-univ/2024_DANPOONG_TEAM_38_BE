package boosters.fundboost.project.dto.request;

import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectBasicInfoRequest {
    @NotNull
    private String mainTitle;

    @NotNull
    private String subTitle;

    @NotNull
    private String image;

    @NotNull
    private ProjectCategory category;

    @NotNull
    private Region region;

    private String account;
    private String budgetDescription;
    private String scheduleDescription;
    private String teamDescription;
    private Long targetAmount;
}
