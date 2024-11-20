package boosters.fundboost.review.service;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.boost.reopsitory.BoostRepository;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.review.converter.ReviewConverter;
import boosters.fundboost.review.domain.Review;
import boosters.fundboost.review.domain.enums.ReviewType;
import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;
import boosters.fundboost.review.repository.ReviewRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.domain.enums.UserType;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final BoostRepository boostRepository;
    private final ReviewConverter reviewConverter;

    @Override
    public ReviewResponseDto createProjectReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트 ID입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        Boost boost = boostRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트에 대한 사용자의 후원 기록이 없습니다."));

        ReviewType reviewType = user.getUserType() == UserType.COMPANY
                ? ReviewType.COMPANY_REVIEW
                : ReviewType.SUPPORTER_REVIEW;

        Review review = reviewConverter.toEntity(reviewRequestDto, reviewType, project, user, boost);

        reviewRepository.save(review);

        return reviewConverter.toResponseDto(review);
    }
    @Override
    public ReviewResponseDto createCompletionReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트 ID입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        if (!project.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("마감후기는 프로젝트 등록자만 작성할 수 있습니다.");
        }

        Review review = reviewConverter.toEntity(
                reviewRequestDto,
                ReviewType.COMPLETION_REVIEW,
                project,
                user,
                null
        );
        reviewRepository.save(review);

        return reviewConverter.toResponseDto(review);
    }
}
