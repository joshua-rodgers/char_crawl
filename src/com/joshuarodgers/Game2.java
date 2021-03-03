package com.joshuarodgers;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Game2 extends KeyAdapter{
    char[][] current_board;
    Gamepiece[][][] dungeon;
    Gamepiece [][] current_map;
    Player player;
    Gamepiece[] pieces;
    Random randu;
    final int dungeon_size;
    Frame frame;
    Panel panel;
    Image buffer;
    Graphics panel_ctx;
    Graphics img_ctx;
    Font font;
    int size;
    Resize_Trigger resizer;
    boolean resized;
    int frame_width;
    int frame_height;
    int font_size;

    public Game2 (int size){
        randu = new Random();
        dungeon_size = get_random(size);
        dungeon = new Gamepiece[dungeon_size][][];
        init_world(size);

        this.resizer = new Resize_Trigger(this);
        resized = false;
        this.size = size;
        frame_width = size * (size + 2);
        frame_height = size * (size + 2);
 
        frame = new Frame();
        panel = new Panel();
        frame.setSize(frame_width, frame_height);
        frame.setBackground(Color.BLACK);
        panel.setSize(frame.getSize());
        panel.setPreferredSize(frame.getSize());
        panel.setMaximumSize(new Dimension(1000, 1000));
        frame.addKeyListener(this);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        frame.addComponentListener(resizer);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        init_graphics();
    }

    public void init_graphics(){
        panel_ctx = panel.getGraphics();
        buffer = panel.createImage(panel.getMaximumSize().width, panel.getMaximumSize().height);
        img_ctx = buffer.getGraphics();
        font_size = 20;
        font = new Font("monospaced", 1, font_size);
        img_ctx.setFont(font);
        img_ctx.setColor(Color.WHITE);
    }
    
    private int get_random(int limit){
        return randu.nextInt(limit + 1);
    }

    private int get_random(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void init_world(int limit){
        for(var room = 0; room < dungeon.length; room++){
            dungeon[room] = new Gamepiece[get_random(4, limit)][get_random(4, limit)];
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

    public void render(){
        int x = (frame_width / 2) - ((int)(font_size * 0.6) * (size / 2));
        int y = (frame_height / 2) - (font_size * (size / 2));
        update();
        img_ctx.clearRect(0, 0, frame.getWidth(), frame.getHeight());
        for(char[] row:current_board){
            img_ctx.drawString(new String(row), x, y);
            y += 20;
        }
        panel_ctx.drawImage(buffer, 0, 0, null);
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

    public void size_recalc(){
        this.frame_width = frame.getWidth();
        this.frame_height = frame.getHeight();
    }

    public void run(){
        while(true){
            try{
                render();
                Thread.sleep(100);
            }catch(Exception ex){
                System.out.println(ex.getMessage());
                break;
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressed = e.getKeyCode();
        switch(pressed){
            case 37:
                player.move("left");
                break;
            case 38:
                player.move("up");
                break;
            case 39:
                player.move("right");
                break;
            case 40:
                player.move("down");
                break;
            default:
        }
    }

    public static void main(String[] args) {
        Game2 g2 = new Game2(20);
        g2.run();
    }
}

class Resize_Trigger extends ComponentAdapter{
    Game2 game;
    public Resize_Trigger(Game2 game){
        this.game = game;
    }
    @Override
    public void componentResized(ComponentEvent e) {
        game.size_recalc();
    }
}

abstract class Gamepiece {
    int row;
    int col;
    char glyph;
    boolean mobile;
    Gamepiece[][] map;

    public void move(String direction){}
}

interface Game_Item{
    public void collected(Player collector);
}

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

class Wall extends Gamepiece{
    public Wall(int row, int col, Gamepiece[][] map){
        this.mobile = false;
        this.row = row;
        this.col = col;
        this.glyph = 'X';
        this.map = map;
    }
}

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

class Door extends Gamepiece{
    boolean is_locked;
    public Door(int row, int col, Gamepiece[][] map, boolean is_locked){
        this.row = row;
        this.col = col;
        this.map = map;
        this.is_locked = is_locked;
        mobile = false;

        if(is_locked){
            this.glyph = '|';
        }else{
            this.glyph = '_';
        }
    }

    public void use_door(Player player){
        if(is_locked){

        }
    }
}


