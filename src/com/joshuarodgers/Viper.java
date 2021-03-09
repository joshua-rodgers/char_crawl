package com.joshuarodgers;

public class Viper extends Enemy implements Runnable {
    public Viper(int row, int col, Gamepiece[][] room){
        this.name = "Viper";
        this.glyph = 'v';
        this.row = row;
        this.col = col;
        this.is_alive = true;
        this.room = room;
    }

    public void run(){

    }
}
