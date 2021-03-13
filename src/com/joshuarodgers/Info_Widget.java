package com.joshuarodgers;
import java.awt.Font;
import java.awt.Color;

public class Info_Widget {
    private Player player;
    private String msg_text;
    private String player_energy;
    private String player_gold;

    Font msg_font;
    int msg_font_size;
    Color msg_font_color;
    boolean expired;
    private long triggered;
    private long duration;

    public Info_Widget(Player player){
        duration = 0;
        triggered = 0;
        expired = true;
        msg_text = "";
        this.player = player;
    }

    public Info_Widget(){
        this(null);
    }

    public void init_widget(int font_size, Color color){
        msg_font_size = 12;
        msg_font = new Font("monospaced", 1, msg_font_size);
        msg_font_color = color;
    }

    public void set_messsage(String message){
        this.msg_text = message;
        this.triggered = System.currentTimeMillis();
        this.expired = false;
    }

    public String get_message(){
        return this.msg_text;
    }

    public String get_gold(){
        return Integer.toString(player.gold);
    }

    public String get_energy(){
        return Integer.toString(player.energy);
    }


    public boolean message_duration(){
        long elapsed = System.currentTimeMillis() - triggered;
        if(duration > 3000){
            expired = true;
            this.msg_text = "";
            triggered = 0;
            duration = 0;
            return true;
        }else{
            duration += elapsed;
            triggered = System.currentTimeMillis();
            //System.out.println(duration);
            return false;
        }
    }


    
}
