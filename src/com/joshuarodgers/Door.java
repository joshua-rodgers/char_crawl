package com.joshuarodgers;

class Door extends Gamepiece{
    boolean is_locked;
    public Door(int row, int col, Gamepiece[][] map, boolean is_locked){
        this.row = row;
        this.col = col;
        this.map = map;
        this.is_locked = is_locked;
        mobile = false;

        if(is_locked){
            this.glyph = '|';
        }else{
            this.glyph = '_';
        }
    }

    public void use_door(Player player){
        if(is_locked){

        }
    }
}