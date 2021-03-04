package com.joshuarodgers;

abstract class Gamepiece {
    int row;
    int col;
    char glyph;
    boolean mobile;
    Gamepiece[][] map;

    public void move(String direction){}
}
