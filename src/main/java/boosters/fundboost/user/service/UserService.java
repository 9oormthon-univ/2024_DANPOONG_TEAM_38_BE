package boosters.fundboost.user.service;

import boosters.fundboost.project.dto.response.ProjectPreviewResponse;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.response.UserMyPageResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    User getUser(String userEmail);

    UserMyPageResponse getMyPage(User user);

    Page<ProjectPreviewResponse> getFavProjects(Long userId, int page);

    Page<ProjectPreviewResponse> getBoostedProjects(Long userId, int page);
}
