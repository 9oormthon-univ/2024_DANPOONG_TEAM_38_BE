package boosters.fundboost.company.dto.response;

public record CompanyRankingRecord(long contributionAmount, long contributionCount ) {
    public static CompanyRankingRecord from(long contributionAmount, long contributionCount) {
        return new CompanyRankingRecord(contributionAmount, contributionCount);
    }
}
