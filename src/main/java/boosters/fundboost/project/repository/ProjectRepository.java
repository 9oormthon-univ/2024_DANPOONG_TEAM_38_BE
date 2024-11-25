package boosters.fundboost.project.repository;

import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.domain.enums.Progress;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, CustomProjectRepository {

    @Query("SELECT p FROM Project p ORDER BY p.createdAt DESC LIMIT 3")
    List<Project> findNewProjects();

    @Query("SELECT p FROM Project p WHERE p.category = :category ORDER BY p.createdAt DESC")
    List<Project> findByCategory(ProjectCategory category);

    @Query("SELECT p FROM Project p WHERE p.region = :region ORDER BY p.createdAt DESC")
    List<Project> findByRegion(Region region);

    @Query("SELECT p FROM Project p LEFT JOIN Like l ON p.id = l.project.id GROUP BY p.id ORDER BY COUNT(l.id) DESC")
    List<Project> findPopularProjects();

    @Query("SELECT p FROM Project p WHERE p.progress = :progress")
    List<Project> findByProgress(Progress progress);

    @Query("SELECT p FROM Project p ORDER BY p.createdAt DESC")
    Page<Project> findAllProjects(Pageable pageable);

    @Query("SELECT p FROM Project p LEFT JOIN Like l ON p.id = l.project.id GROUP BY p.id ORDER BY COUNT(l.id) DESC")
    Page<Project> findPopularProjects(Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<Project> findByUserId(@Param("userId") Long userId);

    Page<Project> findAllByUser_Id(@Param("userId") Long userId, Pageable pageable);

    long countByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT p FROM Project p JOIN FETCH p.user WHERE p.id = :projectId")
    Optional<Project> findProjectWithUserById(@Param("projectId") Long projectId);

}