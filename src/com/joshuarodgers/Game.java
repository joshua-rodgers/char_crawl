package com.joshuarodgers;


public class Game{
    Game_Graphics g_gfx;
    Game_Input g_input;
    Game_Utils g_utilities;

    char[][] current_board;
    Gamepiece[][][] dungeon;
    Gamepiece [][] current_map;
    Player player;
    Gamepiece[] pieces;

    final int dungeon_size;
    int size;

    public Game (int size){
        g_input = new Game_Input(this);
        g_gfx = new Game_Graphics(this, size);
        g_utilities = new Game_Utils();

        dungeon_size = g_utilities.get_random(size);
        dungeon = new Gamepiece[dungeon_size][][];
        init_world(size);
        this.size = size;
        g_gfx.init_graphics();
    }



    public void init_world(int limit){
        for(var room = 0; room < dungeon.length; room++){
            dungeon[room] = new Gamepiece[g_utilities.get_random(4, limit)][g_utilities.get_random(4, limit)];
        }
        Gamepiece[][] first_room = dungeon[0];
        current_board = new char[first_room.length][first_room[0].length];
        
        current_map = first_room;
        player = new Player(current_map);


        for(int row = 0; row < current_map.length; row++){
            for(int col = 0; col < current_map[row].length; col++){
                if(row == 0 || row == current_map.length - 1){
                    current_map[row][col] = new Wall(row, col, current_map);
                }else if(col == 0 || col == current_map[row].length - 1){
                    current_map[row][col] = new Wall(row, col, current_map);
                }else{
                    current_map[row][col] = null;
                }
            }
        }
        current_map[player.row][player.col] = player;
        //current_map[9][12] = new Coin(9, 12, current_map);
        //current_map[16][4] = new Coin(16, 4, current_map);
    }

    public void update(){
        for(int row = 0; row < current_map.length; row++){
            for(int col = 0; col < current_map[row].length; col++){
                if(current_map[row][col] == null){
                    current_board[row][col] = ' ';
                }else{
                    current_board[row][col] = current_map[row][col].glyph;
                }
            }
        }
    }

    public void run(){
        while(true){
            try{
                g_gfx.render();
                Thread.sleep(100);
            }catch(Exception ex){
                System.out.println(ex.getMessage());
                break;
            }
        }

    }

    public static void main(String[] args) {
        Game game = new Game(20);
        game.run();
    }
}
















