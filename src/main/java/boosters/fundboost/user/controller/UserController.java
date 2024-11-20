package boosters.fundboost.user.controller;

import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.global.security.handler.annotation.AuthUser;
import boosters.fundboost.project.dto.response.ProjectPreviewResponse;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.response.UserMyPageResponse;
import boosters.fundboost.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "User 👤", description = "유저 관련 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "마이페이지 조회 API", description = "마이페이지를 조회 합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("/mypage")
    public BaseResponse<UserMyPageResponse> myPage(@Parameter(name = "user", hidden = true) @AuthUser User user) {
        return BaseResponse.onSuccess(SuccessStatus._OK, userService.getMyPage(user));
    }

    @Operation(summary = "관심 프로젝트 조회 API", description = "관심 프로젝트를 조회 합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("/favorites")
    public BaseResponse<Page<ProjectPreviewResponse>> getFavProjects(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                     @Parameter(name = "user", hidden = true) @AuthUser User user) {
        return BaseResponse.onSuccess(SuccessStatus._OK, userService.getFavProjects(user.getId(), page));
    }

    @Operation(summary = "후원한 프로젝트 조회 API", description = "후원한 프로젝트를 조회 합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("/boosts")
    public BaseResponse<Page<ProjectPreviewResponse>> getBoostedProjects(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @Parameter(name = "user", hidden = true) @AuthUser User user) {
        return BaseResponse.onSuccess(SuccessStatus._OK, userService.getBoostedProjects(user.getId(), page));
    }
}
