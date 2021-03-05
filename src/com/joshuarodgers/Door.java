package com.joshuarodgers;

class Door extends Gamepiece{
    boolean is_locked;
    Gamepiece[][] room_1;
    Gamepiece[][] room_2;
    private Gamepiece[][] current;
    private boolean first_set;
    int used;
    
    public Door(boolean is_locked){
        this.is_locked = is_locked;
        used = 0;
        mobile = false;

        if(is_locked){
            this.glyph = '|';
        }else{
            this.glyph = '_';
        }
    }

    public void set_first(int row, int col, Gamepiece[][] room_1){
        this.row = row;
        this.col = col;
        this.room_1 = room_1;
        first_set = true;
    }

    public void set_second(int row, int col, Gamepiece[][] room_2){
        if(first_set){
            this.row = row;
            this.col = col;
            this.room_2 = room_2;
        }
    }

    public Gamepiece[][] use_door(Player player){
        used++;
        if(is_locked){
            return null;
        }else{
            if(used % 2 == 0){
                current = room_2;
                return current;
            }else{
                current = room_1;
                return current;
            }
        }

    }
}