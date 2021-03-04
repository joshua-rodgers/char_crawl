package com.joshuarodgers;

import java.util.Random;
public class Game_Utils {
    Random Randu;

    public Game_Utils(){
        Randu = new Random();
    }

    public int get_random(int limit){
        return Randu.nextInt(limit + 1);
    }

    public int get_random(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
