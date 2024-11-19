package boosters.fundboost.project.domain.enums;

public enum ProjectCategory {
    ACCESSORY("소품"),
    ART("미술"),
    BEAUTY("뷰티"),
    GAME("게임"),
    MUSIC("음악"),
    WEB_APP("웹/앱");

    private final String name;

    ProjectCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
