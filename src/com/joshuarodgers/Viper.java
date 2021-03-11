package com.joshuarodgers;

public class Viper extends Enemy {
    private long start_time;
    private long current_time;
    private long wait_time;

    static final String attack_type = "bite";

    public Viper(int row, int col, Gamepiece[][] room, Game game){
        this.name = "Viper";
        this.glyph = 'v';
        this.power = utilities.get_random(3);
        this.energy = utilities.get_random(10);
        this.level = utilities.get_random(5);
        this.row = row;
        this.col = col;
        this.is_alive = true;
        this.start_time = System.currentTimeMillis();
        this.current_time = 0;
        this.wait_time = 0;
        this.room = room;
        this.game = game;
    }

    public void live(){
        //int steps = Enemy.utilities.get_random(1, room.length - 1);
        //int steps = 3;
        byte direction;
        if(loiter()){
            if(is_alive){
                direction = (byte)Enemy.utilities.get_random(3);
                move(direction);
            }
        }
    }

    public boolean loiter(){
        long elapsed = System.currentTimeMillis() - start_time;
        if(wait_time > 1000){
            wait_time = 0;
            start_time = System.currentTimeMillis();
            return true;
        }else{
            wait_time += elapsed;
            return false;
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
        game.player.energy -= power * level;
        game.message_board("You were bitten by a viper!");
        System.out.println(game.player.energy);
    }
}
