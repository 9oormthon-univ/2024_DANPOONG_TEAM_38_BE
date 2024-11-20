package boosters.fundboost.follow.repository;

import boosters.fundboost.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    long countAllByFollowingId(Long userId);

    long countAllByFollowerId(Long userId);

    Optional<Follow> findFollowByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
