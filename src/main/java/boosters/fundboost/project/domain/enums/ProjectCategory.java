package boosters.fundboost.project.domain.enums;

public enum ProjectCategory {
    ACCESSORY(0),
    ART(1),
    BEAUTY(2),
    GAME(3),
    MUSIC(4),
    WEB_APP(5);

    private final int value;

    ProjectCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ProjectCategory fromValue(int value) {
        for (ProjectCategory category : values()) {
            if (category.value == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
