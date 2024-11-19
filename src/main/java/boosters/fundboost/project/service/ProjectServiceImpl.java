package boosters.fundboost.project.service;

import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.global.uploader.S3Uploader;
import boosters.fundboost.project.converter.ProjectConverter;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.domain.enums.Progress;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final ProjectConverter projectConverter;

    @Override
    public void registerBasicInfo(ProjectBasicInfoRequest request, MultipartFile image) {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        String imageUrl = s3Uploader.upload(image, "project-images");
        Project project = projectConverter.toEntity(request, imageUrl, user);
        projectRepository.save(project);
    }

    @Override
    public List<NewProjectResponse> getNewProjects() {
        List<Project> projects = projectRepository.findNewProjects();
        return projectConverter.toNewProjectsResponse(projects);
    }

    @Override
    public List<NewProjectResponse> getProjectsByCategory(ProjectCategory category) {
        List<Project> projects = projectRepository.findByCategory(category);
        return projectConverter.toNewProjectsResponse(projects);
    }

    @Override
    public List<NewProjectResponse> getProjectsByRegion(Region region) {
        List<Project> projects = projectRepository.findByRegion(region);
        return projectConverter.toNewProjectsResponse(projects);
    }
    @Override
    public List<NewProjectResponse> getPopularProjects() {
        List<Project> projects = projectRepository.findPopularProjects();
        return projectConverter.toNewProjectsResponse(projects);
    }
    @Override
    public List<NewProjectResponse> getCorporateFundingProjects() {
        var projects = projectRepository.findByProgress(Progress.CORPORATE_FUNDING);
        return projectConverter.toNewProjectsResponse(projects);
    }
    @Override
    public Page<NewProjectResponse> getAllProjects(Pageable pageable) {
        Page<Project> projects = projectRepository.findAllProjects(pageable);
        return projects.map(projectConverter::toNewProjectResponse);
    }
    @Override
    public List<NewProjectResponse> getUserProjects() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<Project> projects = projectRepository.findByUserId(userId);
        return projectConverter.toNewProjectsResponse(projects);
    }
}