package boosters.fundboost.review.domain.enums;

public enum ReviewType {
    COMPANY_REVIEW("기업 후기"),
    SUPPORTER_REVIEW("후원자 후기"),
    COMPLETION_REVIEW("마감 후기");

    private final String name;

    ReviewType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}