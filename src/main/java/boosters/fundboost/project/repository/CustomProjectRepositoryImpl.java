package boosters.fundboost.project.repository;

import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.domain.QProject;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static boosters.fundboost.boost.domain.QBoost.boost;
import static boosters.fundboost.company.domain.QCompany.company;

@RequiredArgsConstructor
public class CustomProjectRepositoryImpl implements CustomProjectRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Tuple> findBoostedCompanies(long projectId, Pageable pageable) {
        Project project = queryFactory.select(QProject.project)
                .from(QProject.project)
                .where(QProject.project.id.eq(projectId))
                .fetchOne();

        List<Tuple> companies = queryFactory
                .select(company, boost.amount.sum())
                .from(boost)
                .where(boost.project.eq(project))
                .join(boost.user.company, company)
                .groupBy(company.id)
                .orderBy(boost.amount.sum().desc())
                .fetch();

        return new PageImpl<>(companies, pageable, companies.size());
    }
}
