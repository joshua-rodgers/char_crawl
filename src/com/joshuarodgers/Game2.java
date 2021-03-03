package com.joshuarodgers;
import java.awt.*;
import java.awt.event.*;

public class Game2 extends KeyAdapter{
    char[][] board;
    Gamepiece [][] map;
    Player player;
    Gamepiece[] pieces;
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
        board = new char[size][size];
        map = new Gamepiece[size][size];
        player = new Player(map);
        
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
        init();
    }

    public void init(){
        
        panel_ctx = panel.getGraphics();
        buffer = panel.createImage(panel.getMaximumSize().width, panel.getMaximumSize().height);
        img_ctx = buffer.getGraphics();
        font_size = 20;
        font = new Font("monospaced", 1, font_size);
        img_ctx.setFont(font);
        img_ctx.setColor(Color.WHITE);

        for(int row = 0; row < map.length; row++){
            for(int col = 0; col < map[row].length; col++){
                if(row == 0 || row == map.length - 1){
                    map[row][col] = new Wall(row, col, map);
                }else if(col == 0 || col == map[row].length - 1){
                    map[row][col] = new Wall(row, col, map);
                }else{
                    map[row][col] = null;
                }
            }
        }
        map[player.row][player.col] = player;
        map[9][12] = new Coin(9, 12, map);
        map[16][4] = new Coin(16, 4, map);
    }

    public void render(){
        int x = (frame_width / 2) - ((int)(font_size * 0.6) * (size / 2));
        int y = (frame_height / 2) - (font_size * (size / 2));
        update();
        img_ctx.clearRect(0, 0, frame.getWidth(), frame.getHeight());
        for(char[] row:board){
            img_ctx.drawString(new String(row), x, y);
            y += 20;
        }
        panel_ctx.drawImage(buffer, 0, 0, null);
    }

    public void update(){
        for(int row = 0; row < map.length; row++){
            for(int col = 0; col < map[row].length; col++){
                if(map[row][col] == null){
                    board[row][col] = ' ';
                }else{
                    board[row][col] = map[row][col].glyph;
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
        this.row = 18;
        this.col = 18;
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


