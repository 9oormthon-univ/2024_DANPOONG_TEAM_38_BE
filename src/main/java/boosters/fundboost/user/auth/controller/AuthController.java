package boosters.fundboost.user.auth.controller;

import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.global.security.handler.annotation.AuthUser;
import boosters.fundboost.global.security.handler.annotation.AuthToken;
import boosters.fundboost.user.auth.dto.AuthResponse.OAuthResponse;
import boosters.fundboost.user.auth.dto.AuthResponse.TokenRefreshResponse;
import boosters.fundboost.user.auth.service.AuthService;
import boosters.fundboost.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth 🔒", description = "인증 관련 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "카카오 로그인 API", description = "인가코드를 받아 카카오 로그인 합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "AUTH400", description = "INVALID_REQUEST_INFO, 잘못된 요청입니다."),
            @ApiResponse(responseCode = "TOKEN400", description = "NOT_FOUND_TOKEN, 토큰을 찾을 수 없습니다."),
    })
    @GetMapping("/kakao/login")
    public BaseResponse<OAuthResponse> kakaoLogin(@RequestParam("code") String code) {
        return BaseResponse.onSuccess(SuccessStatus._OK, authService.kakaoLogin(code));
    }

    @Operation(summary = "카카오 로그아웃 API", description = "카카오 로그아웃 합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "TOKEN400", description = "NOT_FOUND_TOKEN, 토큰을 찾을 수 없습니다."),
    })
    @DeleteMapping("/kakao/logout")
    public BaseResponse<Long> kakaoLogout(@Parameter(name = "user", hidden = true) @AuthUser User user) {
        authService.kakaoLogout(user);
        return BaseResponse.onSuccess(SuccessStatus._OK, user.getId());
    }

    @Operation(summary = "토큰 재발급 API", description = "리프레시 토큰을 검증하고 토큰을 재발급 합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "TOKEN400", description = "NOT_FOUND_TOKEN, 토큰을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "TOKEN402", description = "INVALID_TOKEN, 유효하지 않은 토큰입니다."),
    })
    @PostMapping("/kakao/refresh")
    public BaseResponse<TokenRefreshResponse> refresh(@Parameter(name = "refreshToken", hidden = true) @AuthToken String refreshToken) {
        return BaseResponse.onSuccess(SuccessStatus._OK, authService.refresh(refreshToken));
    }
}
