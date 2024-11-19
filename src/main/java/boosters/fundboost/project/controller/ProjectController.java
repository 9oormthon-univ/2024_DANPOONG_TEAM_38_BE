package boosters.fundboost.project.controller;

import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import boosters.fundboost.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
@Tag(name = "Project 📜", description = "프로젝트 관련 API")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "신규 등록 프로젝트 조회 API", description = "신규 등록된 프로젝트를 조회하고 3가지를 리턴합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("/new")
    public BaseResponse<List<NewProjectResponse>> getNewProjects() {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getNewProjects());
    }
}
