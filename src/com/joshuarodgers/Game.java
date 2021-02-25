package com.joshuarodgers;
import java.awt.*;
import java.awt.event.*;

public class Game {
    Frame frame;
    Panel surface;
    Image buffer;
    Graphics buffer_context;
    Graphics surface_context;
    Game_Model data;
    Font font;

    public Game(){
        data = new Game_Model();
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
        buffer_context.clearRect(0, 0, surface.getWidth(), surface.getHeight());
        String line;
        int x_position = (int)Math.floor((frame.getWidth() / 2) - ((buffer_context.getFont().getSize() * 0.6) * 10)); 
        int y_position = (int)Math.floor((frame.getHeight() / 2) - (buffer_context.getFont().getSize() * 10)); 
        
        for(char[] row:data.board){
            line = new String(row);
            buffer_context.drawString(line, x_position, y_position);
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
    char[][] board;
    Game_Char player;

    public Game_Model(){
        board = new char[20][20];
        player = new Game_Char(18, 18);
    }

    public void init(){
        for(int row = 0;row < board.length; row++){
            for(int col = 0;col < board[0].length; col++){
                if(row == 0 || row == board.length - 1){
                    board[row][col] = 'X';
                }else if(col == 0 || col == board[0].length - 1){
                    board[row][col] = 'X';
                }else{
                    board[row][col] = ' ';
                }
            }
        }
        board[player.location.row][player.location.column] = '@';
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        //super.keyPressed(e);
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
                    board[player.location.row][player.location.column] = ' ';
                    player.location.column--;
                    board[player.location.row][player.location.column] = '@';
                }
                break;
            case "UP":
                if(player.location.row > 1){
                    board[player.location.row][player.location.column] = ' ';
                    player.location.row--;
                    board[player.location.row][player.location.column] = '@';
                }
                break;
            case "RIGHT":
                if(player.location.column < board[player.location.row].length - 2){
                    board[player.location.row][player.location.column] = ' ';
                    player.location.column++;
                    board[player.location.row][player.location.column] = '@';
                }
                break;
            case "DOWN":
                if(player.location.row < board.length - 2){
                    board[player.location.row][player.location.column] = ' ';
                    player.location.row++;
                    board[player.location.row][player.location.column] = '@';
                }
                break;
            default:
        }
    }
}

class Game_Char {
    Position location;

    public Game_Char(int r, int c){
        this.location = new Position(r, c);
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
