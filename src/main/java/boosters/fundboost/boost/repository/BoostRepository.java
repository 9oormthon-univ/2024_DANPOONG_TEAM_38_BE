package boosters.fundboost.boost.repository;

import boosters.fundboost.boost.domain.Boost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoostRepository extends JpaRepository<Boost, Long> {
    Page<Boost> findAllByUser_Id(Long userId, Pageable pageable);
}
