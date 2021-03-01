package com.joshuarodgers;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Broken_Game {
    Frame frame;
    Panel surface;
    Image buffer;
    Graphics buffer_context;
    Graphics surface_context;
    Game_Model data;
    Font font;

    public Game(){
        data = new Game_Model(20);
        data.init();
        frame = new Frame();
        frame.setSize(500, 500);
        frame.setUndecorated(true);
        frame.setBackground(Color.BLACK);
        frame.addKeyListener(data);
        surface = new Panel();
        surface.setSize(frame.getSize());
        surface.setPreferredSize(frame.getSize());
        frame.add(surface);
        frame.pack();
        frame.setVisible(true);
    }

    public void init(){
        buffer = surface.createImage(surface. getWidth(), surface.getHeight());
        buffer_context = buffer.getGraphics();
        surface_context = surface.getGraphics();
        font = new Font("monospaced", 1, 20);
        buffer_context.setFont(font);
        buffer_context.setColor(Color.WHITE);
    }

    public void render(){
        data.update_board();
        buffer_context.clearRect(0, 0, surface.getWidth(), surface.getHeight());
        //String line;
        int x_position = (int)Math.floor((frame.getWidth() / 2) - ((buffer_context.getFont().getSize() * 0.6) * 10)); 
        int y_position = (int)Math.floor((frame.getHeight() / 2) - (buffer_context.getFont().getSize() * 10)); 
        
        for(StringBuilder row:data.board){
            
            buffer_context.drawString(row.toString(), x_position, y_position);
            y_position += 20;
        }
        
        surface_context.drawImage(buffer, 0, 0, null);
    }

    public void run(){
        boolean flag = true;

        while(flag){
            try{
                this.render();
                Thread.sleep(500);
            }catch(Exception e){
                e.printStackTrace();
                flag = false;
            }
        }
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.init();
        g.run();
    }
}

class Game_Model extends KeyAdapter {
    //char[][] board;
    StringBuilder[] board;
    Game_Char[][] map;
    Hero player;
    ArrayList<Integer> modified;

    public Game_Model(int size){
        //board = new char[size][size];
        board = new StringBuilder[size];
        map = new Game_Char[size][size];
        player = new Hero(18, 18);
        modified = new ArrayList<Integer>();
    }

    public void init(){
        for(int row = 0;row < map.length; row++){
            for(int col = 0;col < map[row].length; col++){
                if(row == 0 || row == map.length - 1){
                    map[row][col] = new Wall(row, col);
                    //board[row][col] = map[row][col].glyph;
                    modified.add(row);
                }else if(col == 0 || col == map[row].length - 1){
                    map[row][col] = new Wall(row, col);
                    //board[row][col] = map[row][col].glyph;
                    modified.add(row);
                }else{
                    map[row][col] = null;
                    //board[row][col] = ' ';
                    modified.add(row);
                }
            }
        }
        map[player.location.row][player.location.column] = player;
    }

    public void update_board(){
        for(int row = 0;row < map.length; row++){
            board[row] = new StringBuilder();
            for(int col = 0;col < map[row].length; col++){
                if(map[row][col] == null){
                    board[row].append(' ');
                }else{
                    board[row].append(map[row][col].glyph);
                }
                
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressed = e.getKeyCode();
        switch(pressed){
            case 37:
                move("LEFT");
                break;
            case 38:
                move("UP");
                break;
            case 39:
                move("RIGHT");
                break;
            case 40:
                move("DOWN");
                break;
            default:
        }

    }

    public void move(String dir){
        switch(dir){
            case "LEFT":
                if(player.location.column > 1){
                    //board[player.location.row][player.location.column] = ' ';
                    player.location.column--;
                    //board[player.location.row][player.location.column] = player.glyph;
                }
                break;
            case "UP":
                if(player.location.row > 1){
                    //board[player.location.row][player.location.column] = ' ';
                    player.location.row--;
                    //board[player.location.row][player.location.column] = player.glyph;
                }
                break;
            case "RIGHT":
                if(player.location.column < board.length - 2){
                    //board[player.location.row][player.location.column] = ' ';
                    player.location.column++;
                    //board[player.location.row][player.location.column] = player.glyph;
                }
                break;
            case "DOWN":
                if(player.location.row < board.length - 2){
                    //board[player.location.row][player.location.column] = ' ';
                    player.location.row++;
                    //board[player.location.row][player.location.column] = player.glyph;
                }
                break;
            default:
        }
    }
}

class Game_Char {
    Position location;
    char glyph;

    public Game_Char(int row, int col, char glyph){
        this.location = new Position(row, col);
        this.glyph = glyph;
    }   
}

class Hero extends Game_Char {
    int energy;
    int gold;

    public Hero(int row, int col) {
        super(row, col, '@');
        energy = 100;
        gold = 0;
    }

}

class Walls extends Game_Char {

    public Wall(int row, int col) {
        super(row, col, 'X');
    }
    
}


class Position {
    int row;
    int column;

    public Position(int row, int col){
        this.row = row;
        this.column = col;
    }
}
