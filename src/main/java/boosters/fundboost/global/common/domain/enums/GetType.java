package boosters.fundboost.global.common.domain.enums;

public enum GetType {
    ALL("all"),
    NEW("new");

    private final String type;

    GetType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

