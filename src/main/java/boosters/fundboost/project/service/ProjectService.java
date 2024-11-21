package boosters.fundboost.project.service;

import boosters.fundboost.boost.dto.BoostedInfoResponse;
import boosters.fundboost.company.dto.request.CompanyRankingPreviewRequest;
import boosters.fundboost.company.dto.response.CompanyRankingPreviewResponse;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import boosters.fundboost.project.dto.request.ProjectBasicInfoRequest;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import boosters.fundboost.project.dto.response.ProjectDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    void registerBasicInfo(ProjectBasicInfoRequest request, MultipartFile image);

    void updateProject(Long projectId, ProjectBasicInfoRequest request, MultipartFile image);

    void deleteProject(Long projectId);

    List<NewProjectResponse> getNewProjects();

    List<NewProjectResponse> getProjectsByCategory(ProjectCategory category);

    List<NewProjectResponse> getProjectsByRegion(Region region);

    List<NewProjectResponse> getPopularProjects();

    List<NewProjectResponse> getCorporateFundingProjects();

    Page<NewProjectResponse> getAllProjects(Pageable pageable);

    List<NewProjectResponse> getUserProjects();

    ProjectDetailResponse getProjectDetail(Long projectId);

    long getProjectCount(String getType);

    Page<CompanyRankingPreviewResponse> getBoostedCompanyRanking(CompanyRankingPreviewRequest request);

    Project findById(long projectId);

    BoostedInfoResponse getBoostedInfo(Long projectId);

    Page<NewProjectResponse> searchProject(String keyword, int page);
}