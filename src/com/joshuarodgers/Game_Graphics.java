package com.joshuarodgers;

import java.awt.*;
import java.awt.event.*;


public class Game_Graphics {
    Game game;
    Frame frame;
    Panel game_panel;
    Panel info_panel;
    Image buffer;
    Image info_buffer;
    Graphics game_panel_ctx;
    Graphics info_panel_ctx;
    Graphics img_ctx;
    Graphics info_ctx;
    Font font;
    Resize_Trigger resizer;

    int frame_width;
    int frame_height;
    int game_panel_width;
    int game_panel_height;
    int info_panel_width;
    int info_panel_height;

    int font_size;
    int size;

    public Game_Graphics(Game game, int size){
        this.game = game;
        this.size = size;
        this.resizer = new Resize_Trigger(this);
        frame_width = size * (size + 2);
        frame_height = size * (size + 2);
        game_panel_width = frame_width;
        game_panel_height = frame_height;
        info_panel_width = game_panel_width / 2;
        info_panel_height = game_panel_height;
 
        frame = new Frame();
        game_panel = new Panel();
        info_panel = new Panel();
        frame.setSize(frame_width, frame_height);
        frame.setBackground(Color.BLACK);
        game_panel.setSize(game_panel_width, game_panel_height);
        game_panel.setPreferredSize(game_panel.getSize());
        info_panel.setSize(info_panel_width, info_panel_height);
        info_panel.setPreferredSize(info_panel.getSize());
        game_panel.setBackground(Color.DARK_GRAY);
        game_panel.setMaximumSize(new Dimension(1000, 1000));
        frame.addKeyListener(game.g_input);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        frame.addComponentListener(resizer);
        frame.setLayout(new FlowLayout(1));
        frame.add(game_panel);
        frame.add(info_panel);
        // TEMP-------------------
        frame.setResizable(false);
        // TEMP-------------------
        frame.pack();
        frame.setVisible(true);
    }

    public void init_graphics(){
        game_panel_ctx = game_panel.getGraphics();
        info_panel_ctx = info_panel.getGraphics();
        buffer = game_panel.createImage(game_panel.getMaximumSize().width, game_panel.getMaximumSize().height);
        info_buffer = info_panel.createImage(info_panel.getWidth(), info_panel.getHeight());
        info_ctx = info_buffer.getGraphics();
        img_ctx = buffer.getGraphics();
        font_size = 20;
        font = new Font("monospaced", 1, font_size);
        img_ctx.setFont(font);
        img_ctx.setColor(Color.WHITE);
        game.msg_game_action.init_widget(12, Color.WHITE);
        info_ctx.setFont(game.msg_game_action.msg_font);
        info_ctx.setColor(game.msg_game_action.msg_font_color);

        
    }

    public void render(){
        int x = (game_panel_width / 2) - ((int)(font_size * 0.6) * (size / 2));
        int y = (game_panel_height / 2) - (font_size * (size / 2));
        game.update();
        img_ctx.clearRect(0, 0, frame.getWidth(), frame.getHeight());
        for(char[] row:game.current_board){
            img_ctx.drawString(new String(row), x, y);
            y += 20;
        }
        
        render_info();
        info_panel_ctx.drawImage(info_buffer, 0, 0, null);
        game_panel_ctx.drawImage(buffer, 0, 0, null);
        
    }

    public void render_info(){
        info_ctx.clearRect(0, 0, info_panel_width, info_panel_height);
        if(!game.msg_game_action.expired){
            info_ctx.drawString(game.msg_game_action.get_message(), game.msg_game_action.msg_font_size, game.msg_game_action.msg_font_size * 2);
            game.msg_game_action.message_duration();
        }

        info_ctx.drawRect(0, 0, info_panel_width - 1, info_panel_height - 1);
        info_ctx.drawString("energy: " + game.info_player_stats.get_energy(), game.msg_game_action.msg_font_size , game.msg_game_action.msg_font_size * 4);
        info_ctx.drawString("gold: " + game.info_player_stats.get_gold(), game.msg_game_action.msg_font_size , game.msg_game_action.msg_font_size * 6);
    }

    public void size_recalc(){
        this.game_panel_width = frame.getWidth() - info_panel_width;
        this.game_panel_height = frame.getHeight() ;
    }
}
