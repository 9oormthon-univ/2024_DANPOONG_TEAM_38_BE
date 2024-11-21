package boosters.fundboost.like.service;

import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.like.domain.Like;
import boosters.fundboost.like.exception.LikeException;
import boosters.fundboost.like.repository.LikeRepository;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.exception.ProjectException;
import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Project> getLikeProjects(Long userId, Pageable pageable) {
        Page<Like> likes = likeRepository.findAllByUser_Id(userId, pageable);

        return likes.map(Like::getProject);
    }

    @Transactional
    @Override
    public boolean toggleLike(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new LikeException(ErrorStatus.PROJECT_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new LikeException(ErrorStatus.USER_NOT_FOUND));

        Optional<Like> existingLike = likeRepository.findByProject_IdAndUser_Id(projectId, userId);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return false;
        } else {
            Like newLike = Like.createLike(project, user);
            likeRepository.save(newLike);
            return true;
        }
    }
    @Override
    public long getLikeCountByProject(Long projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));

        return likeRepository.countByProjectId(projectId);
    }

}
