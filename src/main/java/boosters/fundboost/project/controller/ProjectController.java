package boosters.fundboost.project.controller;

import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "프로젝트 생성 API", description = "프로젝트 기본 정보, 펀딩계획, 프로젝트 계획, 창작자 정보를 등록합니다"
    )
    public ResponseEntity<String> createProject(@ModelAttribute ProjectBasicInfoRequest request) {
        projectService.registerBasicInfo(request, request.getImage());
        return ResponseEntity.ok("프로젝트가 성공적으로 생성되었습니다.");
    }
}