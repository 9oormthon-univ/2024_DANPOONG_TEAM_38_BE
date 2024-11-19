package boosters.fundboost.user.service;

import boosters.fundboost.follow.service.FollowService;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.user.converter.UserMyPageConverter;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.response.UserMyPageResponse;
import boosters.fundboost.user.exception.UserException;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FollowService followService;

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
}
