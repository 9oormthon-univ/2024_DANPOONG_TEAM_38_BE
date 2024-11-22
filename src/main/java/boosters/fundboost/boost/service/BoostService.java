package boosters.fundboost.boost.service;

import boosters.fundboost.project.domain.Project;
import boosters.fundboost.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BoostService {
    Page<Project> getBoostedProjects(Long userId, Pageable pageable);

    void boostCompany(User user, Project project, long amount);

    Map<Long, Long> getBoostedAmounts(List<Long> projectIds);
}
