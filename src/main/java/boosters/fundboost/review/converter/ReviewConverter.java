package boosters.fundboost.review.converter;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.review.domain.Review;
import boosters.fundboost.review.domain.enums.ReviewType;
import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;
import boosters.fundboost.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    public Review toEntity(ReviewRequestDto dto, ReviewType reviewType, Project project, User user, Boost boost) {
        return new Review(
                dto.getTitle(),
                dto.getDescription(),
                reviewType,
                project,
                user,
                boost
        );
    }

    public ReviewResponseDto toResponseDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                "리뷰가 성공적으로 등록되었습니다."
        );
    }
}