package boosters.fundboost.like.service;

import boosters.fundboost.like.domain.Like;
import boosters.fundboost.like.repository.LikeRepository;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.repository.ProjectRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean toggleLike(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트를 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

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
}
