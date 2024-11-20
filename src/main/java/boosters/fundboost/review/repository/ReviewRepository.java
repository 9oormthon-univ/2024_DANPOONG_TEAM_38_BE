package boosters.fundboost.review.repository;

import boosters.fundboost.review.domain.Review;
import boosters.fundboost.review.domain.enums.ReviewType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProjectIdAndReviewType(Long projectId, ReviewType reviewType); // 메서드 선언
}
