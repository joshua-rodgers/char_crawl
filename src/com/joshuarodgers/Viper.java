package com.joshuarodgers;

public class Viper extends Enemy {
    public Viper(int row, int col, Gamepiece[][] room){
        this.name = "Viper";
        this.glyph = 'v';
        this.row = row;
        this.col = col;
        this.room = room;
    }

    public void live(){
        
    }
}
