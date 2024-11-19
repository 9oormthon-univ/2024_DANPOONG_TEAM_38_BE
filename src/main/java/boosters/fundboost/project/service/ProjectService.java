package boosters.fundboost.project.service;

import boosters.fundboost.project.dto.response.NewProjectResponse;

import java.util.List;

public interface ProjectService {
    List<NewProjectResponse> getNewProjects();
}
