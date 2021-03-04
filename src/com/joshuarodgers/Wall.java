package com.joshuarodgers;

class Wall extends Gamepiece{
    public Wall(int row, int col, Gamepiece[][] map){
        this.mobile = false;
        this.row = row;
        this.col = col;
        this.glyph = 'X';
        this.map = map;
    }
}
