package boosters.fundboost.review.repository;

import boosters.fundboost.review.domain.Review;
import boosters.fundboost.review.domain.enums.ReviewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r, b.amount FROM Review r " +
            "LEFT JOIN Boost b ON r.user.id = b.user.id AND r.project.id = b.project.id " +
            "WHERE r.project.id = :projectId AND r.reviewType = :reviewType")
    List<Object[]> findReviewsWithAmountByProjectIdAndType(@Param("projectId") Long projectId,
                                                           @Param("reviewType") ReviewType reviewType);
}
