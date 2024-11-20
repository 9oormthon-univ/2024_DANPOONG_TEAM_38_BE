package boosters.fundboost.boost.service;

import boosters.fundboost.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoostService {
    Page<Project> getBoostedProjects(Long userId, Pageable pageable);
}
