package boosters.fundboost.project.converter;

import boosters.fundboost.global.dto.response.PeerProjectResponse;
import boosters.fundboost.global.utils.AmountUtil;
import boosters.fundboost.global.utils.CalculatorUtil;
import boosters.fundboost.global.utils.PeriodUtil;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.domain.ProjectImage;
import boosters.fundboost.project.domain.enums.Progress;
import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.dto.response.MyProjectResponse;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import boosters.fundboost.project.dto.response.ProjectDetailResponse;
import boosters.fundboost.project.dto.response.ProjectPreviewResponse;
import boosters.fundboost.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProjectConverter {

    public Project toEntity(ProjectBasicInfoRequest request, List<String> imageUrls, User user) {
        Project project = Project.builder()
                .mainTitle(request.getMainTitle())
                .subTitle(request.getSubTitle())
                .category(request.getCategory())
                .region(request.getRegion())
                .user(user)
                .account(request.getAccount())
                .budgetDescription(request.getBudgetDescription())
                .scheduleDescription(request.getScheduleDescription())
                .teamDescription(request.getTeamDescription())
                .targetAmount(request.getTargetAmount())
                .introduction(request.getIntroduction())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .summary(request.getSummary())
                .build();

        List<ProjectImage> projectImages = imageUrls.stream()
                .map(url -> ProjectImage.builder()
                        .imageUrl(url)
                        .project(project)
                        .build())
                .collect(Collectors.toList());

        project.getImages().addAll(projectImages);

        return project;
    }


    public NewProjectResponse toNewProjectResponse(Project project, long achievedAmount) {
        double progressRate = CalculatorUtil.calculateProgressRate(achievedAmount, project.getTargetAmount());

        String imageUrl = project.getImages().isEmpty() ? null : project.getImages().get(0).getImageUrl();

        return NewProjectResponse.builder()
                .id(project.getId())
                .mainTitle(project.getMainTitle())
                .image(imageUrl)
                .category(project.getCategory().getName())
                .region(project.getRegion().getName())
                .progressRate(progressRate)
                .achievedAmount(AmountUtil.formatAmount(achievedAmount))
                .isCorporateFunding(project.getProgress() == Progress.CORPORATE_FUNDING)
                .progressPeriod(PeriodUtil.localDateToPeriodFormat(project.getStartDate(), project.getEndDate()))
                .userName(project.getUser().getName())
                .build();
    }

    public ProjectDetailResponse toProjectDetailResponse(Project project) {
        List<String> imageUrls = project.getImages().stream()
                .map(ProjectImage::getImageUrl)
                .collect(Collectors.toList());

        return ProjectDetailResponse.builder()
                .id(project.getId())
                .mainTitle(project.getMainTitle())
                .subTitle(project.getSubTitle())
                .images(imageUrls) // 다중 이미지 지원
                .introduction(project.getIntroduction())
                .budgetDescription(project.getBudgetDescription())
                .scheduleDescription(project.getScheduleDescription())
                .teamDescription(project.getTeamDescription())
                .summary(project.getSummary())
                .build();
    }

    public void updateEntity(Project project, ProjectBasicInfoRequest request, List<String> imageUrls) {
        project.getImages().clear();

        List<ProjectImage> projectImages = imageUrls.stream()
                .map(url -> ProjectImage.builder()
                        .imageUrl(url)
                        .project(project)
                        .build())
                .collect(Collectors.toList());

        project.getImages().addAll(projectImages);

        project.updateBasicInfo(
                request.getMainTitle(),
                request.getSubTitle(),
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
        return projects.map(project -> {
            String imageUrl = project.getImages().isEmpty() ? null : project.getImages().get(0).getImageUrl();
            return ProjectPreviewResponse.builder()
                    .image(imageUrl)
                    .userName(project.getUser().getName())
                    .mainTitle(project.getMainTitle())
                    .period(PeriodUtil.localDateToPeriodFormat(project.getStartDate(), project.getEndDate()))
                    .progress(project.getProgress().name())
                    .category(project.getCategory().getName())
                    .daysLeft(CalculatorUtil.calculateDaysLeft(project.getStartDate(), project.getEndDate()))
                    .build();
        });
    }
    public List<MyProjectResponse> toMyProjectsResponse(List<Project> projects) {
        return projects.stream()
                .map(this::toMyProjectResponse)
                .collect(Collectors.toList());
    }

    public MyProjectResponse toMyProjectResponse(Project project) {
        String imageUrl = project.getImages().isEmpty() ? null : project.getImages().get(0).getImageUrl();
        LocalDate now = LocalDate.now();
        boolean isFunding = now.isBefore(project.getEndDate()) && now.isAfter(project.getStartDate());
        Integer daysRemaining = now.isBefore(project.getEndDate()) ?
                Period.between(now, project.getEndDate()).getDays() : null;

        return MyProjectResponse.builder()
                .id(project.getId())
                .mainTitle(project.getMainTitle())
                .image(imageUrl)
                .category(project.getCategory().getName())
                .progressStatus(project.getProgress().name())
                .progressPeriod(PeriodUtil.localDateToPeriodFormat(project.getStartDate(), project.getEndDate()))
                .isFunding(isFunding)
                .daysRemaining(daysRemaining)
                .build();
    }

    public static List<PeerProjectResponse> toPeerProjectListResponse(Map<Project, Long> projectInfo) {
        return projectInfo.keySet().stream()
                .map(project -> {
                    Long achievementAmount = projectInfo.get(project);
                    return ProjectConverter.toPeerProjectResponse(project, achievementAmount);
                })
                .toList();
    }

    private static PeerProjectResponse toPeerProjectResponse(Project project, Long achievedAmount) {
        double progressRate = CalculatorUtil.calculateProgressRate(achievedAmount, project.getTargetAmount());

        return PeerProjectResponse.builder()
                .id(project.getId())
                .mainTitle(project.getMainTitle())
                .introduction(project.getIntroduction())
                .image(project.getImages().get(0).getImageUrl())
                .targetAmount(AmountUtil.formatAmount(project.getTargetAmount()))
                .progressRate(progressRate)
                .achievedAmount(AmountUtil.formatAmount(achievedAmount))
                .progressPeriod(PeriodUtil.localDateToPeriodFormat(project.getStartDate(), project.getEndDate()))
                .build();
    }
}
