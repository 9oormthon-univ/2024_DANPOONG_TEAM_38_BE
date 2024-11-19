package boosters.fundboost.project.domain.enums;

public enum Region {
    SEOUL(0),
    BUSAN(1),
    DAEGU(2),
    INCHEON(3),
    GWANGJU(4),
    DAEJEON(5),
    ULSAN(6),
    JEJU(7);

    private final int value;

    Region(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Region fromValue(int value) {
        for (Region region : values()) {
            if (region.value == value) {
                return region;
            }
        }
        throw new IllegalArgumentException("Invalid region value: " + value);
    }
}

