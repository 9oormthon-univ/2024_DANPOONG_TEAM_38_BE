package boosters.fundboost.company.repository;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.dto.response.CompanyRankingRecord;

import java.util.Map;

public interface CustomCompanyRepository {
    Map<Company, CompanyRankingRecord> findCompanies(String sortType);
}
