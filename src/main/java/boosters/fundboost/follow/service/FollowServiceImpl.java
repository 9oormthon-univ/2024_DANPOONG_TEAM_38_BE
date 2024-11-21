package boosters.fundboost.follow.service;

import boosters.fundboost.follow.domain.Follow;
import boosters.fundboost.follow.exception.FollowException;
import boosters.fundboost.follow.repository.FollowRepository;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.utils.UserValidator;
import boosters.fundboost.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;

    @Override
    public long getFollowingCount(User user) {
        return followRepository.countAllByFollowerId(user.getId());
    }

    @Override
    public long getFollowerCount(User user) {
        return followRepository.countAllByFollowingId(user.getId());
    }

    @Override
    @Transactional
    public void followUser(User user, long followedUserId) {
        UserValidator.validateFollow(user.getId(), followedUserId);

        Follow follow = Follow.builder()
                .followerId(user.getId())
                .followingId(followedUserId)
                .user(user)
                .build();

        followRepository.save(follow);
    }

    @Override
    @Transactional
    public void unfollowUser(User user, long unFollowedUser) {
        Follow follow = followRepository
                .findFollowByFollowerIdAndFollowingId(user.getId(), unFollowedUser)
                .orElseThrow(() -> new FollowException(ErrorStatus.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }
}
