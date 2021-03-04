package com.joshuarodgers;

import java.awt.event.*;

class Resize_Trigger extends ComponentAdapter{
    Game_Graphics g_gfx;
    public Resize_Trigger(Game_Graphics gfx){
        this.g_gfx = gfx;
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        g_gfx.size_recalc();
    }
}
