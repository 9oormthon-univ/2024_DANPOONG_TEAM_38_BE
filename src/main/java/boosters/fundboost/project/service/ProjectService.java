package boosters.fundboost.project.service;

import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void registerBasicInfo(ProjectBasicInfoRequest request) {
        // 로그인한 사용자 조회 (SecurityUtils 사용)
        Long userId = SecurityUtils.getCurrentUserId(); // 로그인한 사용자 ID 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // Project 엔티티 생성 및 저장
        Project project = Project.builder()
                .mainTitle(request.getMainTitle())
                .subTitle(request.getSubTitle())
                .image(request.getImage())
                .category(request.getCategory()) // Enum as tinyint
                .region(request.getRegion()) // Enum as tinyint
                .account(request.getAccount()) // Optional
                .budgetDescription(request.getBudgetDescription()) // Optional
                .scheduleDescription(request.getScheduleDescription()) // Optional
                .teamDescription(request.getTeamDescription()) // Optional
                .targetAmount(request.getTargetAmount()) // Optional
                .user(user)
                .build();


        projectRepository.save(project);
    }
}