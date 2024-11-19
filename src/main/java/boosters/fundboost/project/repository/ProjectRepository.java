package boosters.fundboost.project.repository;

import boosters.fundboost.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p ORDER BY p.createdAt DESC")
    List<Project> findNewProjects();
}