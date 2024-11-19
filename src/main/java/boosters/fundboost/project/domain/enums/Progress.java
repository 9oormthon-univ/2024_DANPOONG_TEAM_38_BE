package boosters.fundboost.project.domain.enums;

public enum Progress {
    PENDING("대기중"),
    APPROVED("승인됨"),
    REJECTED("반려됨");

    private final String name;

    Progress(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
