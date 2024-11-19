package boosters.fundboost.global.common.domain.enums;

public enum SortType {
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    ALLTIME("alltime"),
    PROJECTS("projects");
    private final String value;

    SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
