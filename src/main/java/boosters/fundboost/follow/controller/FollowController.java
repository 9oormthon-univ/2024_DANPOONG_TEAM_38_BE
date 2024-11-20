package boosters.fundboost.follow.controller;

import boosters.fundboost.follow.service.FollowService;
import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.global.security.handler.annotation.AuthUser;
import boosters.fundboost.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Follow 👨‍👩‍👧‍👦", description = "팔로우 관련 API")
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "팔로우 요청 API", description = "팔로우를 요청합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON202", description = "_NO_CONTENT, 요청 성공 및 반환할 콘텐츠가 없음"),
            @ApiResponse(responseCode = "USER400", description = "USER_NOT_FOUND, 팔로우 할 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "FOLLOW401", description = "FOLLOW_YOURSELF, 자신과 팔로우 관계를 가질 수 없습니다."),
    })
    @PostMapping("/follow/{followedUser}")
    public BaseResponse<?> followingUser(@Parameter(name = "user", hidden = true) @AuthUser User user,
                                         @PathVariable("followedUser") long followedUser) {
        followService.followUser(user, followedUser);
        return BaseResponse.onSuccess(SuccessStatus._NO_CONTENT, null);
    }

    @Operation(summary = "팔로우 취소 API", description = "팔로우를 취소합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON202", description = "_NO_CONTENT, 요청 성공 및 반환할 콘텐츠가 없음"),
            @ApiResponse(responseCode = "FOLLOW400", description = "FOLLOW_NOT_FOUND, 팔로우 정보를 찾을 수 없습니다."),
    })
    @PostMapping("/unfollow/{unFollowedUser}")
    public BaseResponse<?> unFollowingUser(@Parameter(name = "user", hidden = true) @AuthUser User user,
                                           @PathVariable(name = "unFollowedUser") long unFollowedUser) {
        followService.unfollowUser(user, unFollowedUser);
        return BaseResponse.onSuccess(SuccessStatus._NO_CONTENT, null);
    }
}
