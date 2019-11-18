package com.company;

public enum Player {
    UNDEFINED(0,"_"),YOU(1,"X"), COMPUTER(2,"O");

    private final int id;
    private final String player;

    Player(int id, String player) {
        this.id = id;
        this.player = player;
    }

    private int getId(){
        return id;
    }

    public String getPlayer(){
        return player;
    }

    public static Player getEnumPlayer(int id){
        for (Player type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
