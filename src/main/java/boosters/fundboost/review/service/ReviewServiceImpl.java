package boosters.fundboost.review.service;

import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.review.converter.ReviewConverter;
import boosters.fundboost.review.domain.Review;
import boosters.fundboost.review.domain.enums.ReviewType;
import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.MyReviewResponseDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;
import boosters.fundboost.review.exception.ReviewException;
import boosters.fundboost.review.repository.ReviewRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.domain.enums.UserType;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ReviewConverter reviewConverter;

    @Override
    public ReviewResponseDto createProjectReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.REVIEW_PROJECT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.REVIEW_USER_NOT_FOUND));

        ReviewType reviewType = user.getUserType() == UserType.COMPANY
                ? ReviewType.COMPANY_REVIEW
                : ReviewType.SUPPORTER_REVIEW;

        Review review = reviewConverter.toEntity(reviewRequestDto, reviewType, project, user, null);

        reviewRepository.save(review);

        return reviewConverter.toResponseDto(review);
    }

    @Override
    public ReviewResponseDto createCompletionReview(Long projectId, Long userId, ReviewRequestDto reviewRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.REVIEW_PROJECT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.REVIEW_USER_NOT_FOUND));

        if (!project.getUser().getId().equals(userId)) {
            throw new ReviewException(ErrorStatus.REVIEW_UNAUTHORIZED_ACCESS);
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
    @Override
    public List<ReviewResponseDto> getReviewsByProjectIdAndType(Long projectId, ReviewType reviewType) {
        List<Object[]> reviewsWithAmount = reviewRepository.findReviewsWithAmountByProjectIdAndType(projectId, reviewType);

        return reviewsWithAmount.stream()
                .map(record -> {
                    Review review = (Review) record[0];
                    BigDecimal amount = record[1] != null
                            ? (record[1] instanceof BigDecimal
                            ? (BigDecimal) record[1]
                            : BigDecimal.valueOf(((Number) record[1]).longValue()))
                            : null;
                    return reviewConverter.toResponseDto(review, amount);
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<MyReviewResponseDto> getMyReviews(Long userId) {
        if (userId == null) {
            throw new ReviewException(ErrorStatus.REVIEW_UNAUTHORIZED_ACCESS);
        }
        List<Review> myReviews = reviewRepository.findByUserId(userId);
        return myReviews.stream()
                .map(reviewConverter::toMyReviewResponseDto)
                .collect(Collectors.toList());
    }
}

