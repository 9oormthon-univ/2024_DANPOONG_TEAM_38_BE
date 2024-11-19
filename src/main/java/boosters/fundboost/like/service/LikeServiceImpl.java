package boosters.fundboost.like.service;

import boosters.fundboost.like.domain.Like;
import boosters.fundboost.like.repository.LikeRepository;
import boosters.fundboost.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Override
    public Page<Project> getLikeProjects(Long userId, Pageable pageable) {
        Page<Like> likes = likeRepository.findAllByUser_Id(userId, pageable);

        return likes.map(Like::getProject);
    }
}
