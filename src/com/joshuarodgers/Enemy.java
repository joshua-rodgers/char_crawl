package com.joshuarodgers;

public abstract class Enemy extends Gamepiece {
    String name;
    Gamepiece[][] room;
    Game game;
    boolean is_alive;
    static Game_Utils utilities = new Game_Utils();
    int energy;
    int power;
    int level;
    String attack_type;

    public void attack(){}
    public void die(){}

}
