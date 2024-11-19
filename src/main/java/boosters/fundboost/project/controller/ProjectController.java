package boosters.fundboost.project.controller;

import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> createProject(@ModelAttribute ProjectBasicInfoRequest request) {
        projectService.registerBasicInfo(request, request.getImage());
        return ResponseEntity.ok("프로젝트가 성공적으로 생성되었습니다.");
    }
}