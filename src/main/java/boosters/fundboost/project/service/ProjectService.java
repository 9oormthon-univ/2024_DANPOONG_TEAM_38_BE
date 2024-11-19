package boosters.fundboost.project.service;

import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {
    void registerBasicInfo(ProjectBasicInfoRequest request, MultipartFile image);
}
