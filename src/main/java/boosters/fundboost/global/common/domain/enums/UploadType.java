package boosters.fundboost.global.common.domain.enums;

public enum UploadType {
    IMAGE("project-images"),
    FILE("proposal-files"),
    ;

    private final String directory;

    UploadType(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
