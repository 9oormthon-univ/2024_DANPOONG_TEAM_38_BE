package boosters.fundboost.company.dto;

import boosters.fundboost.company.domain.Company;

public record CompanyRankingRecord(Company company, long contributionAmount, long countributionCount) {
    public static CompanyRankingRecord from(Company company, long contributionAmount, long countributionCount) {
        return new CompanyRankingRecord(company, contributionAmount, countributionCount);
    }
}
