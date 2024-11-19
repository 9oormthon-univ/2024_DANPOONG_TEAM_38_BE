package boosters.fundboost.project.controller;

import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/basic-info")
    public ResponseEntity<String> registerBasicInfo(@Validated @RequestBody ProjectBasicInfoRequest request) {
        projectService.registerBasicInfo(request);
        return ResponseEntity.ok("프로젝트 기본정보가 성공적으로 등록되었습니다.");
    }
}