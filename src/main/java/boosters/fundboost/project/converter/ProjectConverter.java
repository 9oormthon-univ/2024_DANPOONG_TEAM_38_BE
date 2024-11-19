package boosters.fundboost.project.converter;

import boosters.fundboost.global.utils.CalculatorUtil;
import boosters.fundboost.global.utils.PeriodUtil;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.dto.response.NewProjectResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectConverter {
    public static List<NewProjectResponse> toNewProjectsResponse(List<Project> projects) {
        return projects.stream()
                .map(ProjectConverter::toNewProjectResponse)
                .collect(Collectors.toList());
    }

    private static NewProjectResponse toNewProjectResponse(Project project) {
        double progressRate = CalculatorUtil.calculateProgressRate(project.getAchievedAmount(), project.getTargetAmount());

        return NewProjectResponse.builder()
                .id(project.getId())
                .mainTitle(project.getMainTitle())
                .image(project.getImage())
                .category(project.getCategory().getValue())
                .region(project.getRegion().getName())
                .progressRate(progressRate)
                .achievedAmount(project.getAchievedAmount())
                .progressPeriod(PeriodUtil.localDateToPeriodFormat(project.getStartDate(), project.getEndDate()))
                .build();
    }
}
