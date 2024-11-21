package boosters.fundboost.like.repository;

import boosters.fundboost.like.domain.Like;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface LikeRepository extends JpaRepository<Like, Long> {
    Page<Like> findAllByUser_Id(Long userId, Pageable pageable);
    Optional<Like> findByProject_IdAndUser_Id(Long projectId, Long userId);
    boolean existsByProjectAndUser(Project project, User user);
    @Query("SELECT COUNT(l) FROM Like l WHERE l.project.id = :projectId")
    long countByProjectId(Long projectId);
}
