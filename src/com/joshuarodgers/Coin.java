package com.joshuarodgers;

class Coin extends Gamepiece implements Game_Item{
    
    public Coin(int row, int col, Gamepiece[][] map){
        this.row = row;
        this.col = col;
        this.glyph = 'c';
        this.map = map;
    }
    
    @Override
    public void collected(Player collector) {
        collector.gold += 10;
        System.out.println("Collected 10 gold!");
        System.out.println(collector.gold);
        Coin me = this;
        map[row][col] = null;
    }

    public void collected(Gamepiece collector){
        //collector.gold += 10;
        Coin me = this;
        map[row][col] = null;
    }
}
