package com.joshuarodgers;

public abstract class Enemy extends Gamepiece {
    String name;
    Gamepiece[][] room;
    boolean is_alive;
    int energy;
    int power;
    int level;

    public void attack(){}
    public void die(){}

}
