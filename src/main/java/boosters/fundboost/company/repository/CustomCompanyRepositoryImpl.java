package boosters.fundboost.company.repository;

import java.util.LinkedHashMap;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.dto.CompanyRankingRecord;
import boosters.fundboost.global.common.domain.enums.SortType;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.response.exception.GeneralException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static boosters.fundboost.boost.domain.QBoost.boost;
import static boosters.fundboost.company.domain.QCompany.company;

@RequiredArgsConstructor
public class CustomCompanyRepositoryImpl implements CustomCompanyRepository {
    private static final long LIMIT_SIZE = 3;
    private static final int WEEK = 7;
    private static final int MONTH = 1;
    private static final int YEAR = 3;
    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Company, CompanyRankingRecord> findCompanies(String sortType) {
        LocalDate today = LocalDate.now();

        Map<Company, CompanyRankingRecord> companies;

        if (sortType == null || sortType.isEmpty() || sortType.equals(SortType.WEEKLY.getValue())) {
            companies = getTopContributingCompaniesByAmount(today.minusDays(WEEK), today);
        } else if (sortType.equals(SortType.MONTHLY.getValue())) {
            companies = getTopContributingCompaniesByAmount(today.minusMonths(MONTH), today);
        } else if (sortType.equals(SortType.ALLTIME.getValue())) {
            companies = getTopContributingCompaniesByAmount(today.minusYears(YEAR), today);
        } else if (sortType.equals(SortType.PROJECTS.getValue())) {
            companies = getTopContributeCompaniesByProjects();
        } else {
            throw new GeneralException(ErrorStatus.INVALID_REQUEST_INFO);
        }

        return companies;
    }

    private Map<Company, CompanyRankingRecord> getTopContributingCompaniesByAmount(LocalDate startDate, LocalDate endDate) {
        List<Tuple> companies = getCompanies(boost.amount.sum(), startDate, endDate);

        return mapToCompanyRankingRecord(companies);
    }

    private Map<Company, CompanyRankingRecord> getTopContributeCompaniesByProjects() {
        List<Tuple> companies = getCompanies(boost.count(), LocalDate.now().minusYears(YEAR), LocalDate.now());

        return mapToCompanyRankingRecord(companies);
    }

    private List<Tuple> getCompanies(NumberExpression numberExpression, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(company, boost.amount.sum(), boost.count())
                .from(boost)
                .join(boost.user.company, company)
                .where(boost.createdAt.between(startDate.atStartOfDay(), endDate.atStartOfDay()))
                .groupBy(company.id)
                .orderBy(numberExpression.desc())
                .limit(LIMIT_SIZE)
                .fetch();
    }

    private Map<Company, CompanyRankingRecord> mapToCompanyRankingRecord(List<Tuple> companies) {
        return companies.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(company),
                        tuple -> CompanyRankingRecord.from(
                                Optional.ofNullable(tuple.get(boost.amount.sum())).orElse(0L),
                                Optional.ofNullable(tuple.get(boost.count())).orElse(0L)
                        ),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
}
