package boosters.fundboost.review.service;

import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto createProjectReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto);

    ReviewResponseDto createCompletionReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto);
}
