package boosters.fundboost.company.repository;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.dto.response.CompanyRankingRecord;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CustomCompanyRepository {
    Map<Company, CompanyRankingRecord> findCompanies(String sortType);

    Page<Tuple> findBoostedCompanies(long projectId, Pageable pageable);
}
