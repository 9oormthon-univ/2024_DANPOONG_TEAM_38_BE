package boosters.fundboost.review.service;

import boosters.fundboost.review.domain.enums.ReviewType;
import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.MyReviewResponseDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    ReviewResponseDto createProjectReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto);

    ReviewResponseDto createCompletionReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto);

    List<ReviewResponseDto> getReviewsByProjectIdAndType(Long projectId, ReviewType reviewType);
    List<MyReviewResponseDto> getMyReviews(Long userId);
}
