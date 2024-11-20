package boosters.fundboost.boost.reopsitory;

import boosters.fundboost.boost.domain.Boost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoostRepository extends JpaRepository<Boost, Long> {
    Optional<Boost> findByUserIdAndProjectId(Long userId, Long projectId);
}
