package com.joshuarodgers;

class Door extends Gamepiece{
    Game game;
    boolean is_locked;
    private boolean is_vertical;
    Gamepiece[][] room_1;
    Gamepiece[][] room_2;
    private Gamepiece[][] current;
    private boolean first_set;
    int row_2;
    int col_2;
    int used;
    
    public Door(boolean is_locked, boolean is_vertical, Game game){
        this.game = game;
        this.is_vertical = is_vertical;
        this.is_locked = is_locked;
        used = 0;
        mobile = false;

        if(is_locked && !is_vertical){
            this.glyph = '\\';
        }else if(is_locked && is_vertical){
            this.glyph = '_';
        }else if(!is_locked && !is_vertical){
            this.glyph = '_';
        }else if(!is_locked && is_vertical){
            this.glyph = '\\';
        }
    }

    public void set_first(int row, int col, Gamepiece[][] room_1){
        this.row = row;
        this.col = col;
        this.room_1 = room_1;
        first_set = true;
    }

    public void set_second(int row_2, int col_2, Gamepiece[][] room_2){
        if(first_set){
            this.row_2 = row_2;
            this.col_2 = col_2;
            this.room_2 = room_2;
        }
    }

    private Gamepiece[][] use_door(){
        used++;
        if(is_locked){
            return null;
        }else{
            if(used % 2 == 0){
                current = room_1;
                return current;
            }else{
                current = room_2;
                return current;
            }
        }

    }

    public void action(){
        Gamepiece[][] next_room = use_door();
        if(next_room != null){
            if(next_room.equals(room_1)){
                game.change_room(next_room, this.row, this.col, this.is_vertical);
            }else{
                game.change_room(next_room, this.row_2, this.col_2, this.is_vertical);
            }
        }
    }
}