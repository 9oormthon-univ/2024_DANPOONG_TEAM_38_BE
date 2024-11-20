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

    // Request DTO -> Entity 변환
    public Review toEntity(ReviewRequestDto dto, ReviewType reviewType, Project project, User user, Boost boost) {
        return new Review(
                dto.getDescription(),   // 리뷰 설명
                reviewType,             // 리뷰 타입
                project,                // 프로젝트
                user,                   // 사용자
                boost                   // Boost 객체
        );
    }

    // Entity -> Response DTO 변환
    public ReviewResponseDto toResponseDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),         // 리뷰 ID
                "리뷰가 성공적으로 등록되었습니다."
        );
    }
}