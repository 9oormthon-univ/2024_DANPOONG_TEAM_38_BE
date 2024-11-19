package boosters.fundboost.project.service;

import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.project.converter.ProjectConverter;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.dto.response.NewProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public List<NewProjectResponse> getNewProjects() {
        List<Project> projects = projectRepository.findNewProjects();
        return ProjectConverter.toNewProjectsResponse(projects);
    }
}
