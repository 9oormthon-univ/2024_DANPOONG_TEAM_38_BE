package boosters.fundboost.company.converter;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.dto.response.CompanyRankingRecord;
import boosters.fundboost.company.dto.response.CompanyRankingResponse;


public class CompanyRankingConverter {

    public static CompanyRankingResponse toCompanyRankingResponse(Company company, CompanyRankingRecord companyRankingRecord) {
        return CompanyRankingResponse.builder()
                .id(company.getId())
                .name(company.getUser().getName())
                .image(company.getUser().getImage())
                .category(company.getCategory().getName())
                .contributionAmount(companyRankingRecord.contributionAmount())
                .contributionCount(companyRankingRecord.countributionCount())
                .build();
    }
}
