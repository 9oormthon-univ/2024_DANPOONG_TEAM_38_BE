package boosters.fundboost.user.domain.enums;

public enum Tag {
    SEASSAK_INVESTOR("새싹 투자자"),
    BEGINNER_INVESTOR("초급 투자자"),
    ADVANCED_INVESTOR("고급 투자자"),
    PERSONAL_INVESTOR("개인 투자자"),
    LONG_TERM_INVESTOR("장기 투자자");

    private final String value;

    Tag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
