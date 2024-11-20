package boosters.fundboost.review.service;

import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.review.domain.Review;
import boosters.fundboost.review.domain.enums.ReviewType;
import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;
import boosters.fundboost.review.repository.ReviewRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public ReviewResponseDto createProjectReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트 ID입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        Review review = new Review(
                reviewRequestDto.getDescription(),
                ReviewType.COMPANY_REVIEW,
                project,
                user
        );

        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(savedReview.getId(), "리뷰가 성공적으로 등록되었습니다.");
    }
}
