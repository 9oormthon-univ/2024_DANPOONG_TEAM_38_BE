package boosters.fundboost.project.controller;

import boosters.fundboost.boost.dto.BoostedInfoResponse;
import boosters.fundboost.company.dto.request.CompanyRankingPreviewRequest;
import boosters.fundboost.company.dto.response.CompanyRankingPreviewResponse;
import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import boosters.fundboost.project.dto.request.NewProjectRequest;
import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import boosters.fundboost.project.dto.response.ProjectDetailResponse;
import boosters.fundboost.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
@Tag(name = "Project 📜", description = "프로젝트 관련 API")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "프로젝트 생성 API", description = "프로젝트 기본 정보, 펀딩계획, 프로젝트 계획, 창작자 정보를 등록합니다")
    public BaseResponse<String> createProject(@ModelAttribute ProjectBasicInfoRequest request) {
        projectService.registerBasicInfo(request, request.getImages());
        return BaseResponse.onSuccess(SuccessStatus._OK, "프로젝트가 성공적으로 생성되었습니다.");
    }

    @Operation(summary = "신규 등록 프로젝트 조회 API", description = "최근 등록된 3개의 프로젝트를 조회합니다. 프로젝트는 등록일자 기준으로 정렬됩니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("/new")
    public BaseResponse<List<NewProjectResponse>> getNewProjects() {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getNewProjects());
    }

    @Operation(summary = "카테고리별 프로젝트 조회 API", description = "특정 카테고리에 해당하는 프로젝트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("/category/{category}")
    public BaseResponse<List<NewProjectResponse>> getProjectsByCategory(@PathVariable ProjectCategory category) {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getProjectsByCategory(category));
    }

    @Operation(summary = "지역별 프로젝트 조회 API", description = "특정 지역에 해당하는 프로젝트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("/region/{region}")
    public BaseResponse<List<NewProjectResponse>> getProjectsByRegion(@PathVariable Region region) {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getProjectsByRegion(region));
    }

    @Operation(summary = "전체 인기 프로젝트 조회 API", description = "좋아요 순으로 정렬된 인기 프로젝트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("/popular")
    public BaseResponse<List<NewProjectResponse>> getPopularProjects() {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getPopularProjects());
    }

    @Operation(summary = "기업 펀딩 프로젝트 조회 API", description = "상태가 기업펀딩인 프로젝트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    @GetMapping("/corporate-funding")
    public BaseResponse<List<NewProjectResponse>> getCorporateFundingProjects() {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getCorporateFundingProjects());
    }

    @Operation(summary = "전체 등록 프로젝트 조회 API", description = "전체 프로젝트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })

    @GetMapping("/all")
    public BaseResponse<Page<NewProjectResponse>> getAllProjects(NewProjectRequest request) {
        Page<NewProjectResponse> response = projectService.getAllProjects(request.toPageable());
        return BaseResponse.onSuccess(SuccessStatus._OK, response);
    }

    @Operation(summary = "로그인 사용자 프로젝트 조회 API", description = "로그인한 사용자가 등록한 프로젝트만 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("/user-projects")
    public BaseResponse<List<NewProjectResponse>> getUserProjects() {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getUserProjects());
    }

    @Operation(summary = "프로젝트 상세 조회 API", description = "프로젝트 ID로 특정 프로젝트를 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("/{projectId}")
    public BaseResponse<ProjectDetailResponse> getProjectDetail(@PathVariable Long projectId) {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getProjectDetail(projectId));
    }

    @Operation(summary = "프로젝트 수정 API", description = "프로젝트 기본 정보 및 관련 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공"),
    })

    @PutMapping(value = "/{projectId}", consumes = {"multipart/form-data"})
    public BaseResponse<String> updateProject(
            @PathVariable Long projectId,
            @ModelAttribute ProjectBasicInfoRequest request) {

        projectService.updateProject(projectId, request, request.getImages());
        return BaseResponse.onSuccess(SuccessStatus._OK, "프로젝트가 성공적으로 수정되었습니다.");
    }

    @Operation(summary = "프로젝트 삭제 API", description = "로그인한 사용자가 등록한 프로젝트만 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("프로젝트가 성공적으로 삭제되었습니다.");
    }

    @Operation(summary = "누적 프로젝트 및 신규 프로젝트 수 조회 API",
            description = "누적 프로젝트 및 신규 프로젝트 수를 조회합니다. 누적은 all, 신규는 new 를 파라미터 값으로 받습니다. _숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, 성공"),
            @ApiResponse(responseCode = "400", description = "INVALID_PARAMETER, 파라미터 값이 잘못되었습니다."),
    })
    @GetMapping("/count")
    public BaseResponse<Long> getProjectCount(@RequestParam(name = "getType") String getType) {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getProjectCount(getType));
    }

    @Operation(summary = "후원한 프로젝트 기업 랭킹 조회 API", description = "해당 프로젝트를 후원한 기업 랭킹을 조회합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("/boosted-ranking")
    public BaseResponse<Page<CompanyRankingPreviewResponse>> getBoostedCompanyRanking(CompanyRankingPreviewRequest request) {
        Page<CompanyRankingPreviewResponse> companies = projectService.getBoostedCompanyRanking(request);
        return BaseResponse.onSuccess(SuccessStatus._OK, companies);
    }

    @Operation(summary = "프로젝트의 후원 정보 조회 API", description = "프로젝트의 후원 정보를 조회합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "PROJECT400", description = "PROJECT_NOT_FOUND, 프로젝트를 찾을 수 없습니다.")
    })
    @GetMapping("/boosted-info")
    public BaseResponse<BoostedInfoResponse> getBoostedInfo(@RequestParam(name = "projectId") Long projectId) {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.getBoostedInfo(projectId));
    }

    @Operation(summary = "프로젝트 검색 API", description = "프로젝트를 검색합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "SEARCH400", description = "KEYWORD_LENGTH_ERROR,검색어는 2글자 이상이어야 합니다."),

    })
    @GetMapping("/search/{keyword}")
    public BaseResponse<Page<NewProjectResponse>> search(@PathVariable(name = "keyword") String keyword,
                                                         @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        return BaseResponse.onSuccess(SuccessStatus._OK, projectService.searchProject(keyword, page));
    }
}