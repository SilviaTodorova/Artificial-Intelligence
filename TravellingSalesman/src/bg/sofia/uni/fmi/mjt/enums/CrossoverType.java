package bg.sofia.uni.fmi.mjt.enums;

public enum CrossoverType {
    ONE_POINT("One point"),
    TWO_POINT("Two points");

    private String name;

    CrossoverType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
