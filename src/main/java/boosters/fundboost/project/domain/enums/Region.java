package boosters.fundboost.project.domain.enums;

public enum Region {
    SEOUL("서울특별시"),
    INCHON("인천광역시"),
    DAEGU("대구광역시"),
    BUSAN("부산광역시"),
    GWANGJU("광주광역시"),
    DAEJEON("대전광역시"),
    ULSAN("울산광역시"),
    JEJU("제주특별자치도"),
    CHUNGCHEON("충청도"),
    GYEONGGI("경기도"),
    GANGWON("강원도"),
    JEOLLA("전라도"),
    GYEONGSANG("경상도");

    private final String name;

    Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
