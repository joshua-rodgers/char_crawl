package com.joshuarodgers;

import java.awt.event.*;

public class Game_Input extends KeyAdapter {
    Game game;
    public Game_Input(Game game){
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("yo.");
        int pressed = e.getKeyCode();
        switch(pressed){
            case 32:
            // test case for generating rooms on the fly
            // test case for moving through rooms
                game.test_next_room();
                break;
            case 37:
                game.player.move("left");
                break;
            case 38:
                game.player.move("up");
                break;
            case 39:
                game.player.move("right");
                break;
            case 40:
                game.player.move("down");
                break;
            default:
        }
    }
}
