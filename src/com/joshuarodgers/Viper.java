package com.joshuarodgers;

public class Viper extends Enemy {
    public Viper(int row, int col, Gamepiece[][] room){
        this.name = "Viper";
        this.glyph = 'v';
        this.row = row;
        this.col = col;
        this.is_alive = true;
        this.room = room;
    }

    public void live(){
        //int steps = Enemy.utilities.get_random(1, room.length - 1);
        //int steps = 3;
        byte direction;
        if(is_alive){
            direction = (byte)Enemy.utilities.get_random(3);
            move(direction);
        }
    }

    @Override
    public void move(int direction) {
        Viper me;
        switch(direction){
            case 0: //left
                if(check(row, col - 1)){
                    me = this;
                    room[row][col] = null;
                    col--;
                    room[row][col] = me;
                }
                break;
            case 1: //up
                if(check(row - 1, col)){
                    me = this;
                    room[row][col] = null;
                    row--;
                    room[row][col] = me;
                }
                break;
            case 2: //right
                if(check(row, col + 1)){
                    me = this;
                    room[row][col] = null;
                    col++;
                    room[row][col] = me;
                }
                break;
            case 3: //down
                if(check(row + 1, col)){
                    me = this;
                    room[row][col] = null;
                    row++;
                    room[row][col] = me;
                }
                break;
            default:
        }
    }

    private boolean check(int row, int col){
        Gamepiece tested = room[row][col];
        if(tested == null){
            return true;
        }
        switch(tested.glyph){
            case 'X':
                return false;
            case '@':
                attack();
                return false;
            default:
                return false;
        }
    }

    @Override
    public void attack() {
        System.out.println("attacked");
    }
}
