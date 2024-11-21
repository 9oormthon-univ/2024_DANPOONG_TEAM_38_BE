package boosters.fundboost.project.converter;

import boosters.fundboost.global.utils.CalculatorUtil;
import boosters.fundboost.global.utils.PeriodUtil;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.domain.enums.Progress;
import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import boosters.fundboost.project.dto.response.ProjectDetailResponse;
import boosters.fundboost.project.dto.response.ProjectPreviewResponse;
import boosters.fundboost.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectConverter {

    public Project toEntity(ProjectBasicInfoRequest request, String imageUrl, User user) {
        return Project.builder()
                .mainTitle(request.getMainTitle())
                .subTitle(request.getSubTitle())
                .image(imageUrl)
                .category(request.getCategory())
                .region(request.getRegion())
                .account(request.getAccount())
                .budgetDescription(request.getBudgetDescription())
                .scheduleDescription(request.getScheduleDescription())
                .teamDescription(request.getTeamDescription())
                .targetAmount(request.getTargetAmount())
                .introduction(request.getIntroduction())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .summary(request.getSummary())
                .user(user)
                .build();
    }

    public List<NewProjectResponse> toNewProjectsResponse(List<Project> projects) {
        return projects.stream()
                .map(this::toNewProjectResponse)
                .collect(Collectors.toList());
    }

    public NewProjectResponse toNewProjectResponse(Project project) {
        double progressRate = CalculatorUtil.calculateProgressRate(project.getAchievedAmount(), project.getTargetAmount());

        return NewProjectResponse.builder()
                .id(project.getId())
                .mainTitle(project.getMainTitle())
                .image(project.getImage())
                .category(project.getCategory().getName())
                .region(project.getRegion().getName())
                .progressRate(progressRate)
                .achievedAmount(project.getAchievedAmount())
                .isCorporateFunding(project.getProgress() == Progress.CORPORATE_FUNDING)
                .progressPeriod(PeriodUtil.localDateToPeriodFormat(project.getStartDate(), project.getEndDate()))
                .userName(project.getUser().getName())
                .build();
    }

    public ProjectDetailResponse toProjectDetailResponse(Project project) {
        return ProjectDetailResponse.builder()
                .id(project.getId())
                .mainTitle(project.getMainTitle())
                .subTitle(project.getSubTitle())
                .image(project.getImage())
                .introduction(project.getIntroduction())
                .budgetDescription(project.getBudgetDescription())
                .scheduleDescription(project.getScheduleDescription())
                .teamDescription(project.getTeamDescription())
                .summary(project.getSummary())
                .build();
    }

    public void updateEntity(Project project, ProjectBasicInfoRequest request, String imageUrl) {
        project.updateBasicInfo(
                request.getMainTitle(),
                request.getSubTitle(),
                imageUrl,
                request.getCategory(),
                request.getRegion(),
                request.getAccount(),
                request.getBudgetDescription(),
                request.getScheduleDescription(),
                request.getTeamDescription(),
                request.getTargetAmount(),
                request.getIntroduction(),
                request.getStartDate(),
                request.getEndDate(),
                request.getSummary()
        );
    }

    public static Page<ProjectPreviewResponse> toProjectPreviewResponse(Page<Project> projects) {
        return projects.map(project -> ProjectPreviewResponse.builder()
                .image(project.getImage())
                .mainTitle(project.getMainTitle())
                .period(PeriodUtil.localDateToPeriodFormat(project.getStartDate(), project.getEndDate()))
                .progress(project.getProgress().name())
                .category(project.getCategory().getName())
                .daysLeft(CalculatorUtil.calculateDaysLeft(project.getStartDate(), project.getEndDate()))
                .build());
    }
}
