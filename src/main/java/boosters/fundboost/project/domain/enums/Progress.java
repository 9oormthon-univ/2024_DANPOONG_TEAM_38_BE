package boosters.fundboost.project.domain.enums;

public enum Progress {
    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    private final int value;

    Progress(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Progress fromValue(int value) {
        for (Progress progress : values()) {
            if (progress.value == value) {
                return progress;
            }
        }
        throw new IllegalArgumentException("Invalid progress value: " + value);
    }
}
