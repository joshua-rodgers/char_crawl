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

    public Game2(int size){
        board = new char[size][size];
        map = new Gamepiece[size][size];
        player = new Player(map);
        //pieces = new Gamepiece[size ^ 2];
        
        frame = new Frame();
        panel = new Panel();
        frame.setSize(size * (size + 1), size * (size + 1));
        panel.setSize(frame.getSize());
        panel.setPreferredSize(frame.getSize());
        frame.addKeyListener(this);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        init();
    }

    public void init(){
        panel_ctx = panel.getGraphics();
        buffer = panel.createImage(frame.getWidth(), frame.getHeight());
        img_ctx = buffer.getGraphics();
        font = new Font("monospaced", 1, 20);
        img_ctx.setFont(font);

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
    }

    public void render(){
        int y = 40;
        update();
        img_ctx.clearRect(0, 0, frame.getWidth(), frame.getHeight());
        for(char[] row:board){
            img_ctx.drawString(new String(row), 40, y);
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

    public void run(){
        while(true){
            try{
                render();
                Thread.sleep(500);
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

abstract class Gamepiece {
    int row;
    int col;
    char glyph;
    Gamepiece[][] map;

    public void move(String direction){}
}

class Player extends Gamepiece{
    public Player(Gamepiece[][] map){
        this.row = 18;
        this.col = 18;
        this.glyph = '@';
        this.map = map;
    }

    @Override
    public void move(String direction) {
        Gamepiece me;
        switch(direction){
            case "left":
                me = this;
                map[this.row][this.col] = null;
                this.col--;
                map[this.row][this.col] = me;
                break;
            case "up":
                me = this;
                map[this.row][this.col] = null;
                this.row--;
                map[this.row][this.col] = me;
                break;
            case "right":
                me = this;
                map[this.row][this.col] = null;
                this.col++;
                map[this.row][this.col] = me;
                break;
            case "down":
                me = this;
                map[this.row][this.col] = null;
                this.row++;
                map[this.row][this.col] = me;
                break;
            default:
        }
    }
}

class Wall extends Gamepiece{
    public Wall(int row, int col, Gamepiece[][] map){
        this.row = row;
        this.col = col;
        this.glyph = 'X';
        this.map = map;
    }
}


