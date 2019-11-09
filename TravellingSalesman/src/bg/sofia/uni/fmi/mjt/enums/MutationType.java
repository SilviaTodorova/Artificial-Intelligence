package bg.sofia.uni.fmi.mjt.enums;

public enum MutationType {
    INSERTION("Insertion"),
    SWAP("Swap");

    private String name;

    MutationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
