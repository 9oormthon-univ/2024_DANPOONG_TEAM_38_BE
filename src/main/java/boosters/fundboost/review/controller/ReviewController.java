package boosters.fundboost.review.controller;

import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;
import boosters.fundboost.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects/{projectId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long projectId,
            @Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        Long userId = SecurityUtils.getCurrentUserId();
        ReviewResponseDto response = reviewService.createProjectReview(projectId, userId, reviewRequestDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/completion")
    public ResponseEntity<ReviewResponseDto> createCompletionReview(
            @PathVariable Long projectId,
            @Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        Long userId = SecurityUtils.getCurrentUserId();
        ReviewResponseDto response = reviewService.createCompletionReview(projectId, userId, reviewRequestDto);
        return ResponseEntity.ok(response);
    }
}
