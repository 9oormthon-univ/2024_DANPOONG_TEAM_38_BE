package boosters.fundboost.project.domain.enums;

public enum Progress {
    DRAFT("작성"),
    REVIEW("심사"),
    FUNDING("펀딩"),
    FUNDING_COMPLETED("펀딩 완료"),
    CORPORATE_FUNDING("기업 펀딩");

    private final String status;

    Progress(String status) {
        this.status = status;
    }
}