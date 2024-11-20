package boosters.fundboost.user.service;

import boosters.fundboost.follow.service.FollowService;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.like.service.LikeService;
import boosters.fundboost.project.converter.ProjectConverter;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.dto.response.ProjectPreviewResponse;
import boosters.fundboost.user.converter.UserMyPageConverter;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.response.UserMyPageResponse;
import boosters.fundboost.user.exception.UserException;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final int PAGE_SIZE = 3;
    private final UserRepository userRepository;
    private final FollowService followService;
    private final LikeService likeService;

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public UserMyPageResponse getMyPage(User user) {
        long followingCount = followService.getFollowingCount(user);
        long followerCount = followService.getFollowerCount(user);
        return UserMyPageConverter.toUserMyPageResponse(user, followingCount, followerCount);
    }

    @Override
    public Page<ProjectPreviewResponse> getFavProjects(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Project> projects = likeService.getLikeProjects(userId, pageable);

        return ProjectConverter.toProjectPreviewResponse(projects);
    }
    @Override
    public UserMyPageResponse getMyPageById(Long userId) {
        User user = getUser(userId);
        return getMyPage(user);
    }

    @Override
    public User findUserById(Long userId) {
        return getUser(userId);
    }
}
