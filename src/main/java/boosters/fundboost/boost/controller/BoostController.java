package boosters.fundboost.boost.controller;

import boosters.fundboost.boost.service.BoostService;
import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.global.security.handler.annotation.AuthUser;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.dto.request.BoostProjectRequest;
import boosters.fundboost.project.service.ProjectService;
import boosters.fundboost.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boost")
@Tag(name = "Boost 🔥", description = "후원 관련 API")
public class BoostController {
    private final BoostService boostService;
    private final ProjectService projectService;

    @Operation(summary = "프로젝트 후원 API", description = "프로젝트에 후원합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "PROJECT400", description = "PROJECT_NOT_FOUND, 프로젝트가 존재하지 않습니다.")
    })
    @PostMapping("")
    public BaseResponse<?> boostCompany(@Parameter(name = "user", hidden = true) @AuthUser User user,
                                        BoostProjectRequest request) {
        Project project = projectService.findById(request.projectId());

        boostService.boostCompany(user, project, request.amount());
        return BaseResponse.of(SuccessStatus._NO_CONTENT, null);
    }
}
