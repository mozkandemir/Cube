package com.mod.cube;

import android.graphics.Color;

/**
 * Created by özkan in 9.01.2017
 */

public class BubblePool {

    public static int SIZE = 0, COUNT = 0;

    public void generateSquare(int size, int count){
        SIZE = size;
        COUNT = count;
        for (int i = 0; i < count; i++) {
            new Square(Calc.random.nextInt(Frame.WIDTH)-100, Calc.random.nextInt(Frame.HEIGHT), (Calc.random.nextInt(size)+1)*100, Color.rgb(Calc.random.nextInt(255), Calc.random.nextInt(255), Calc.random.nextInt(255)), false);
        }
    }
}
