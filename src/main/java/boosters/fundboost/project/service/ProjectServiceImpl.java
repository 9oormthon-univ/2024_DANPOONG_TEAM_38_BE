package boosters.fundboost.project.service;

import boosters.fundboost.boost.converter.BoostedInfoConverter;
import boosters.fundboost.boost.dto.BoostedInfoResponse;
import boosters.fundboost.boost.repository.BoostRepository;
import boosters.fundboost.company.converter.CompanyRankingConverter;
import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.dto.request.CompanyRankingPreviewRequest;
import boosters.fundboost.company.dto.response.CompanyRankingPreviewRecord;
import boosters.fundboost.company.dto.response.CompanyRankingPreviewResponse;
import boosters.fundboost.global.common.domain.enums.GetType;
import boosters.fundboost.global.dto.response.PeerProjectResponse;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.security.util.SecurityUtils;
import boosters.fundboost.global.uploader.S3Uploader;
import boosters.fundboost.global.utils.SearchValidator;
import boosters.fundboost.project.converter.ProjectConverter;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.domain.enums.Progress;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import boosters.fundboost.project.dto.response.ProjectDetailResponse;
import boosters.fundboost.project.exception.ProjectException;
import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.exception.UserException;
import boosters.fundboost.user.repository.UserRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final static int PAGE_SIZE = 3;
    private final static int SEARCH_PAGE_SIZE = 16;
    private final static int COMPANY_RANKING_INDEX = 0;
    private final static int CONTRIBUTION_AMOUNT_INDEX = 1;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final BoostRepository boostRepository;
    private final S3Uploader s3Uploader;
    private final ProjectConverter projectConverter;

    public void registerBasicInfo(ProjectBasicInfoRequest request, List<MultipartFile> images) {
        List<String> imageUrls = images.stream()
                .map(image -> s3Uploader.upload(image, "project-images"))
                .collect(Collectors.toList());

        Project project = projectConverter.toEntity(request, imageUrls, getCurrentUser());
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

    public Page<PeerProjectResponse> getUserProjects(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Page<Project> projects = projectRepository.findByUserId(userId, pageable);
        Map<Project, Long> projectInfo = projects.getContent().stream()
                .collect(Collectors.toMap(
                        project -> project,
                        project -> boostRepository.sumAmountByProject_Id(project.getId())
                ));

        List<PeerProjectResponse> peerProjects = ProjectConverter.toPeerProjectListResponse(projects, projectInfo);

        return new PageImpl<>(peerProjects, pageable, projects.getTotalElements());
    }

    @Override
    public ProjectDetailResponse getProjectDetail(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
        return projectConverter.toProjectDetailResponse(project);
    }

    private User getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public void updateProject(Long projectId, ProjectBasicInfoRequest request, List<MultipartFile> images) {
        Long userId = SecurityUtils.getCurrentUserId();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));

        if (!project.getUser().getId().equals(userId)) {
            throw new ProjectException(ErrorStatus.UNAUTHORIZED_ACCESS);
        }

        List<String> imageUrls = images.stream()
                .map(img -> s3Uploader.upload(img, "project-images"))
                .collect(Collectors.toList());

        projectConverter.updateEntity(project, request, imageUrls);
        projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Long userId = SecurityUtils.getCurrentUserId();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
        if (!project.getUser().getId().equals(userId)) {
            throw new ProjectException(ErrorStatus.UNAUTHORIZED_ACCESS);
        }
        projectRepository.delete(project);
    }

    @Override
    public long getProjectCount(String getType) {
        if (getType.equals(GetType.ALL.getType())) {
            return projectRepository.count();
        } else if (getType.equals(GetType.NEW.getType())) {
            return projectRepository.countByCreatedAtAfter(LocalDate.now().atStartOfDay());
        }
        throw new ProjectException(ErrorStatus.INVALID_PARAMETER);
    }

    @Override
    public Page<CompanyRankingPreviewResponse> getBoostedCompanyRanking(CompanyRankingPreviewRequest request) {
        Pageable pageable = PageRequest.of(request.page(), PAGE_SIZE);
        Page<Tuple> companies = projectRepository.findBoostedCompanies(request.projectId(), pageable);

        List<CompanyRankingPreviewResponse> responses = companies.getContent().stream()
                .map(tuple -> CompanyRankingConverter.toCompanyRankingPreviewResponse(
                        Objects.requireNonNull(tuple.get(COMPANY_RANKING_INDEX, Company.class)),
                        new CompanyRankingPreviewRecord(tuple.get(CONTRIBUTION_AMOUNT_INDEX, Long.class))
                ))
                .toList();

        return new PageImpl<>(responses, pageable, companies.getTotalElements());
    }

    @Override
    public BoostedInfoResponse getBoostedInfo(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));

        long boostedUserCount = boostRepository.countByProject_Id(projectId);
        long achievementAmount = boostRepository.sumAmountByProject_Id(projectId);

        return BoostedInfoConverter.toBoostedInfoResponse(project, achievementAmount, boostedUserCount);
    }

    @Override
    public Page<NewProjectResponse> searchProject(String keyword, int page) {
        SearchValidator.ValidateKeyword(keyword);
        Pageable pageable = PageRequest.of(page, SEARCH_PAGE_SIZE);

        Page<Project> projects = projectRepository.searchProject(keyword, pageable);
        return projectConverter.toNewProjectPageResponse(projects);
    }

    @Override
    public Project findById(long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
    }
}