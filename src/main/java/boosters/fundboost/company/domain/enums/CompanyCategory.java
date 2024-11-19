package boosters.fundboost.company.domain.enums;

public enum CompanyCategory {
    ACCESSORY("소품"),
    ART("미술"),
    BEAUTY("뷰티"),
    GAME("게임"),
    MUSIC("음악"),
    WEB_APP("웹/앱");

    private final String name;

    CompanyCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
