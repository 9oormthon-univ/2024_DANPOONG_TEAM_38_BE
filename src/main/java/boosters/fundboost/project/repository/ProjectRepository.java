package boosters.fundboost.project.repository;

import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p ORDER BY p.createdAt DESC LIMIT 3")
    List<Project> findNewProjects();

    @Query("SELECT p FROM Project p WHERE p.category = :category ORDER BY p.createdAt DESC")
    List<Project> findByCategory(ProjectCategory category);

    @Query("SELECT p FROM Project p WHERE p.region = :region ORDER BY p.createdAt DESC")
    List<Project> findByRegion(Region region);
}