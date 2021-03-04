package com.joshuarodgers;

class Player extends Gamepiece{
    int energy;
    int gold;

    public Player(Gamepiece[][] map){
        this.row = map.length - 2;
        this.col = map[0].length - 2;
        this.glyph = '@';
        this.map = map;

        this.energy = 100;
        this.gold = 0;
    }

    public boolean check(int row, int col){
        Gamepiece tested = map[row][col];
        if(tested == null){
            return true;
        }

        switch(tested.glyph){
            case 'X':
                if(tested.mobile){
                    return true;
                }else{
                    return false;
                }
                
            case 'c':
                collect((Game_Item)tested);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void move(String direction) {
        Gamepiece me;
        switch(direction){
            case "left":
                if(check(this.row, this.col - 1)){
                    me = this;
                    map[this.row][this.col] = null;
                    this.col--;
                    map[this.row][this.col] = me;
                }
                
                break;
            case "up":
                if(check(this.row - 1, this.col)){
                    me = this;
                    map[this.row][this.col] = null;
                    this.row--;
                    map[this.row][this.col] = me;
                }
                break;
            case "right":
                if(check(this.row, this.col + 1)){
                    me = this;
                    map[this.row][this.col] = null;
                    this.col++;
                    map[this.row][this.col] = me;
                }
                break;
            case "down":
                if(check(this.row + 1, this.col)){
                    me = this;
                    map[this.row][this.col] = null;
                    this.row++;
                    map[this.row][this.col] = me;
                }
                break;
            default:
        }
    }

    public void collect(Game_Item item){
        item.collected(this);
    }
}