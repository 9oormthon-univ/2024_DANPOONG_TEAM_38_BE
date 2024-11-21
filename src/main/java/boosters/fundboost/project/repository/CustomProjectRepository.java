package boosters.fundboost.project.repository;

import boosters.fundboost.project.domain.Project;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomProjectRepository {
    Page<Tuple> findBoostedCompanies(long projectId, Pageable pageable);

    Page<Project> searchProject(String keyword, Pageable pageable);
}
