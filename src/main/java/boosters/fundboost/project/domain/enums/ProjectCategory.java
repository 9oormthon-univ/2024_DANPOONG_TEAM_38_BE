package boosters.fundboost.project.domain.enums;

public enum ProjectCategory {
    ACCESSORY("소품"),
    ART("미술"),
    BEAUTY("뷰티"),
    GAME("게임"),
    MUSIC("음악"),
    WEB_APP("웹/앱");

    private final String value;

    ProjectCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProjectCategory fromValue(String value) {
        for (ProjectCategory category : values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
