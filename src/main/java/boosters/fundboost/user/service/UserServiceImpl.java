package boosters.fundboost.user.service;

import boosters.fundboost.boost.service.BoostService;
import boosters.fundboost.follow.service.FollowService;
import boosters.fundboost.global.common.domain.enums.UploadType;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.uploader.S3UploaderService;
import boosters.fundboost.global.utils.MyPageValidator;
import boosters.fundboost.like.service.LikeService;
import boosters.fundboost.project.converter.ProjectConverter;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.dto.response.BoostedProjectResponse;
import boosters.fundboost.project.dto.response.ProjectPreviewResponse;
import boosters.fundboost.user.converter.MyPageConverter;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.request.ProfileEditRequest;
import boosters.fundboost.user.dto.response.PeerMyPageResponse;
import boosters.fundboost.user.dto.response.UserMyPageResponse;
import boosters.fundboost.user.exception.UserException;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final int PAGE_SIZE = 3;
    private final UserRepository userRepository;
    private final FollowService followService;
    private final LikeService likeService;
    private final BoostService boostService;
    private final S3UploaderService s3UploaderService;

    @Override
    public User getUser(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public UserMyPageResponse getMyPage(User user) {
        long followingCount = followService.getFollowingCount(user);
        long followerCount = followService.getFollowerCount(user);
        return MyPageConverter.toUserMyPageResponse(user, followingCount, followerCount);
    }

    @Override
    public Page<ProjectPreviewResponse> getFavProjects(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Project> projects = likeService.getLikeProjects(userId, pageable);

        return ProjectConverter.toProjectPreviewResponse(projects);
    }

    @Override
    public Page<BoostedProjectResponse> getBoostedProjects(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Project> projects = boostService.getBoostedProjects(userId, pageable);
        Map<Long, Long> boostedAmounts = boostService.getBoostedAmounts(
                projects.stream().map(Project::getId).toList()
        );

        return projects.map(project -> {
            Long boostedAmount = boostedAmounts.getOrDefault(project.getId(), 0L);
            return ProjectConverter.toBoostedProjectResponse(project, boostedAmount);
        });
    }

    @Override
    @Transactional
    public void editProfile(User user, ProfileEditRequest request) {
        String imageUrl = Optional.ofNullable(request.getImage())
                .filter(image -> !image.isEmpty())
                .map(image -> s3UploaderService.uploadImage(image, UploadType.IMAGE.getDirectory()))
                .orElse(null);

        user.updateUser(request, imageUrl);
    }

    @Override
    public PeerMyPageResponse getPeerProfile(Long peerId, Long userId) {
        MyPageValidator.validatePeerId(peerId, userId);

        User user = userRepository.findById(peerId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        long followingCount = followService.getFollowingCount(user);
        long followerCount = followService.getFollowerCount(user);

        return MyPageConverter.toPeerMyPageResponse(user, followingCount, followerCount);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}