package boosters.fundboost.review.controller;

import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;
import boosters.fundboost.review.service.ReviewService;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.domain.enums.UserType;
import boosters.fundboost.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        System.out.println("Before fetching userId from SecurityUtils");
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("Fetched userId: " + userId);

        ReviewResponseDto response = reviewService.createProjectReview(projectId, userId, reviewRequestDto);
        return ResponseEntity.ok(response);
    }

}
