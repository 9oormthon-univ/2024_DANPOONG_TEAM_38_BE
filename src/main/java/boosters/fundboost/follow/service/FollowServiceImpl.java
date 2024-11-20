package boosters.fundboost.follow.service;

import boosters.fundboost.follow.repository.FollowRepository;
import boosters.fundboost.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
