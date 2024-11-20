package boosters.fundboost.review.controller;

import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.review.dto.request.ReviewRequestDto;
import boosters.fundboost.review.dto.response.ReviewResponseDto;
import boosters.fundboost.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Review 📝", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "리뷰 작성 API", description = "사용자가 특정 프로젝트에 대해 리뷰를 생성합니다. 리뷰 유형은 사용자의 타입에 따라 자동으로 결정됩니다 (기업 리뷰, 후원자 리뷰).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long projectId,
            @Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        Long userId = SecurityUtils.getCurrentUserId();
        ReviewResponseDto response = reviewService.createProjectReview(projectId, userId, reviewRequestDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/completion")
    @Operation(summary = "마감 후기 작성 API", description = "프로젝트 등록자가 마감 후기를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ResponseEntity<ReviewResponseDto> createCompletionReview(
            @PathVariable Long projectId,
            @Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        Long userId = SecurityUtils.getCurrentUserId();
        ReviewResponseDto response = reviewService.createCompletionReview(projectId, userId, reviewRequestDto);
        return ResponseEntity.ok(response);
    }
}
