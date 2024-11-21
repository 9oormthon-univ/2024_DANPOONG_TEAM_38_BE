package boosters.fundboost.like.service;

import boosters.fundboost.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeService {
    Page<Project> getLikeProjects(Long userId, Pageable pageable);
    boolean toggleLike(Long projectId, Long userId);
    long getLikeCountByProject(Long projectId);
}
