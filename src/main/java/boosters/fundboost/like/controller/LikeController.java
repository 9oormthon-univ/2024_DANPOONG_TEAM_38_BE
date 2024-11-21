package boosters.fundboost.like.controller;

import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PatchMapping("/{projectId}")
    @Operation(summary = "좋아요 토글 API", description = "로그인한 사용자가 특정 프로젝트에 좋아요를 추가하거나 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공적으로 좋아요 추가/취소"),
    })
    public ResponseEntity<String> toggleLike(@PathVariable Long projectId) {
        Long userId = SecurityUtils.getCurrentUserId(); // 로그인된 사용자 ID 가져오기
        boolean isLiked = likeService.toggleLike(projectId, userId); // 좋아요 상태 변경
        String message = isLiked ? "좋아요를 추가했습니다." : "좋아요를 취소했습니다.";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{projectId}/count")
    @Operation(summary = "프로젝트 좋아요 수 조회", description = "특정 프로젝트의 좋아요 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공적으로 좋아요 수 반환"),
    })
    public ResponseEntity<Map<String, Object>> getLikeCountByProject(@PathVariable Long projectId) {
        long likeCount = likeService.getLikeCountByProject(projectId);
        Map<String, Object> response = Map.of("likeCount", likeCount);
        return ResponseEntity.ok(response);
    }
}