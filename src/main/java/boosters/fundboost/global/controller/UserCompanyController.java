package boosters.fundboost.global.controller;

import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.global.security.handler.annotation.AuthUser;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.request.ProfileEditRequest;
import boosters.fundboost.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "User-Company 👤🧑‍💼", description = "유저, 회사 공통 API")
public class UserCompanyController {
    private final UserService userService;

    @Operation(summary = "프로필 편집 API", description = "프로필을 편집합니다. " +
            "프로필사진, 이름, 대표 링크, 소개글 제목, 내용을 수정할 수 있습니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON202", description = "NO_CONTENT, 요청 성공 및 반환할 콘텐츠가 없음"),
    })
    @PatchMapping(value = "/edit-profile", consumes = {"multipart/form-data"})
    public BaseResponse<?> editProfile(
            @Parameter(name = "user", hidden = true) @AuthUser User user,
            @ModelAttribute ProfileEditRequest request) {
        userService.editProfile(user, request);
        return BaseResponse.onSuccess(SuccessStatus._NO_CONTENT, null);
    }
}
