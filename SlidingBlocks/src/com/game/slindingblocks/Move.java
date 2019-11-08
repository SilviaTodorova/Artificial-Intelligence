package com.game.slindingblocks;

public enum Move {
    UP ("Up"),
    DOWN ("Down"),
    LEFT ("Left"),
    RIGHT ("Right");

    private final String position;

    private Move(String position) {
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }
}
