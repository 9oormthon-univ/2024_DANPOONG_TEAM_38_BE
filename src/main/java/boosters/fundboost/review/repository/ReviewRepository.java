package boosters.fundboost.review.repository;

import boosters.fundboost.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}