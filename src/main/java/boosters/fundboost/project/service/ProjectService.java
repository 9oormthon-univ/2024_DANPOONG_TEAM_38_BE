package boosters.fundboost.project.service;

import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    void registerBasicInfo(ProjectBasicInfoRequest request, MultipartFile image);
    List<NewProjectResponse> getNewProjects();
}