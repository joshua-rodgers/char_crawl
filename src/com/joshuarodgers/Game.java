package com.joshuarodgers;

public class Game{
    Game_Graphics g_gfx;
    Game_Input g_input;
    Game_Utils g_utilities;

    char[][] current_board;
    Gamepiece[][][] dungeon;
    Gamepiece [][] current_map;
    int current_map_idx;
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
        current_map_idx = 0;
        current_board = new char[first_room.length][first_room[0].length];

        for(Gamepiece[][] room:dungeon){
            build_room(room);
        }

        place_doors(dungeon);

        current_map = first_room;
        player = new Player(current_map);
        

        current_map[player.row][player.col] = player;
        //current_map[9][12] = new Coin(9, 12, current_map);
        //current_map[16][4] = new Coin(16, 4, current_map);
    }

    private void build_room(Gamepiece[][] room){
        
        for(int row = 0; row < room.length; row++){
            for(int col = 0; col < room[row].length; col++){
                if(row == 0 || row == room.length - 1){
                    room[row][col] = new Wall(row, col, room);
                }else if(col == 0 || col == room[row].length - 1){
                    room[row][col] = new Wall(row, col, room);
                }else{
                    room[row][col] = null;
                }
            }

        }
    }

    private void place_doors(Gamepiece[][][] dungeon){
        //int start_idx = g_utilities.get_random(dungeon_size);
        int rem_doors = dungeon_size - 1;
        int last_idx = 0;

        Gamepiece[][] current_room = dungeon[0];
        int current_idx = 0;

        int last_wall = -1;

        while(rem_doors > 0){
            //int wall = g_utilities.get_random(4);
            int wall = 0;
            int position;
            Door current_door = new Door(false);
            switch(wall){
                case 0:
                    position = g_utilities.get_random(1, current_room.length - 2);
                    if(current_idx < dungeon.length - 1){
                        if(dungeon[current_idx][position][0].glyph == 'X'){
                            dungeon[current_idx][position][0] = current_door;
                        }else{
                            break;
                        }
                        
                        current_door.set_first(position, 0, current_room);
                        current_idx++;
                        current_room = dungeon[current_idx];
                        position = g_utilities.get_random(1, current_room.length - 2);
                        dungeon[current_idx][position][dungeon[current_idx][position].length - 1] = current_door;
                        current_door.set_second(position, dungeon[current_idx][position].length - 1, current_room);
                        rem_doors--;
                    }
                    
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }

        }

    }

    public void test_next_room(){
        if(current_map_idx < dungeon.length - 1){
            current_map_idx++;
            current_map = dungeon[current_map_idx];
            current_board = new char[current_map.length][current_map[0].length];
        }else{
            current_map_idx = 0;
            current_map = dungeon[current_map_idx];
            current_board = new char[current_map.length][current_map[0].length];
        }
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
















