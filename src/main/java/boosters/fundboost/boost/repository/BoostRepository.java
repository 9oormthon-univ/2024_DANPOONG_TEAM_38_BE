package boosters.fundboost.boost.repository;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.project.dto.response.BoostedAmountProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoostRepository extends JpaRepository<Boost, Long> {
    Page<Boost> findAllByUser_Id(Long userId, Pageable pageable);

    long countByProject_Id(Long projectId);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Boost b WHERE b.project.id = :projectId")
    long sumAmountByProject_Id(Long projectId);

    @Query("SELECT b.project.id AS projectId, SUM(b.amount) AS boostedAmount " +
            "FROM Boost b WHERE b.project.id IN :projectIds " +
            "GROUP BY b.project.id")
    List<BoostedAmountProjection> findBoostedAmountsByProjectIds(@Param("projectIds") List<Long> projectIds);
}
