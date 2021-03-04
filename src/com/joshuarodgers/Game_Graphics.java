package com.joshuarodgers;

import java.awt.*;
import java.awt.event.*;


public class Game_Graphics {
    Game game;
    Frame frame;
    Panel panel;
    Image buffer;
    Graphics panel_ctx;
    Graphics img_ctx;
    Font font;
    Resize_Trigger resizer;

    int frame_width;
    int frame_height;
    int font_size;
    int size;

    public Game_Graphics(Game game, int size){
        this.game = game;
        this.size = size;
        this.resizer = new Resize_Trigger(this);
        frame_width = size * (size + 2);
        frame_height = size * (size + 2);
 
        frame = new Frame();
        panel = new Panel();
        frame.setSize(frame_width, frame_height);
        frame.setBackground(Color.BLACK);
        panel.setSize(frame.getSize());
        panel.setPreferredSize(frame.getSize());
        panel.setMaximumSize(new Dimension(1000, 1000));
        frame.addKeyListener(game.g_input);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        frame.addComponentListener(resizer);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
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

    public void render(){
        int x = (frame_width / 2) - ((int)(font_size * 0.6) * (size / 2));
        int y = (frame_height / 2) - (font_size * (size / 2));
        game.update();
        img_ctx.clearRect(0, 0, frame.getWidth(), frame.getHeight());
        for(char[] row:game.current_board){
            img_ctx.drawString(new String(row), x, y);
            y += 20;
        }
        panel_ctx.drawImage(buffer, 0, 0, null);
    }

    public void size_recalc(){
        this.frame_width = frame.getWidth();
        this.frame_height = frame.getHeight();
    }
}
