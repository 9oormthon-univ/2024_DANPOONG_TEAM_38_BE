package boosters.fundboost.company.converter;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.domain.enums.CompanyCategory;
import boosters.fundboost.company.dto.response.CompanyRankingPreviewRecord;
import boosters.fundboost.company.dto.response.CompanyRankingPreviewResponse;
import boosters.fundboost.company.dto.response.CompanyRankingRecord;
import boosters.fundboost.company.dto.response.CompanyRankingResponse;

import java.util.Optional;


public class CompanyRankingConverter {

    public static CompanyRankingResponse toCompanyRankingResponse(Company company, CompanyRankingRecord companyRankingRecord) {
        return CompanyRankingResponse.builder()
                .id(company.getId())
                .name(company.getUser().getName())
                .image(company.getUser().getImage())
                .category(Optional.ofNullable(company.getCategory())
                        .map(CompanyCategory::getName)
                        .orElse(null))
                .contributionAmount(companyRankingRecord.contributionAmount())
                .contributionCount(companyRankingRecord.contributionCount())
                .build();
    }

    public static CompanyRankingPreviewResponse toCompanyRankingPreviewResponse(Company company, CompanyRankingPreviewRecord record) {
        return CompanyRankingPreviewResponse.builder()
                .companyName(company.getUser().getName())
                .contributionAmount(record.contributionAmount())
                .image(company.getUser().getImage())
                .build();
    }
}
