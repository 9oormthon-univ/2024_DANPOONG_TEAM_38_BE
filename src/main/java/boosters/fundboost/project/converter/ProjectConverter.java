package boosters.fundboost.project.converter;

import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.user.domain.User;
import org.springframework.stereotype.Component;

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
                .user(user)
                .build();
    }
}
