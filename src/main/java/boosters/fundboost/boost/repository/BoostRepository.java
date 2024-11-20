package boosters.fundboost.boost.repository;

import boosters.fundboost.boost.domain.Boost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoostRepository extends JpaRepository<Boost, Long> {
    Page<Boost> findAllByUser_Id(Long userId, Pageable pageable);

    long countByProject_Id(Long projectId);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Boost b WHERE b.project.id = :projectId")
    long sumAmountByProject_Id(Long projectId);
}
