package com.mod.cube;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Log;

public class Square extends Element{

    private Rect rect;
    private int color;
    private boolean move;
    private int initialX, initialY;
    private int degree = 0;
    private float rotationDirection = 0, rotation = 0;
    private double speedX = 0, speedY = 0;

    public Square(int x, int y, int size, int color, boolean move) {
        super();
        rect = new Rect(x, y, x+size, y+size);
        this.color = color;
        this.move = move;
        this.initialX = getX();
        this.initialY = getY();
        rotation = Calc.random.nextInt(360);
        if(Calc.random.nextBoolean()) rotationDirection = 1;
        else rotationDirection = -1;
        float a = Calc.random.nextFloat()+1.5f;
        rotationDirection*=a;
        speedX = Calc.random.nextDouble()*2-1;
        speedY = Calc.random.nextDouble()*2-1;
        super.setSize(size);
    }

    @Override
    public void tick() {
        rotation+= rotationDirection;

        if(rotation > 360) rotation = 0;
        else if(rotation < 0) rotation = 360;

        if(move){
            setX(getX()+Calc.map(Math.sin(Math.toRadians(degree)),0,1,speedX*(getSize()),0));
            setY(getY()+Calc.map(Math.sin(Math.toRadians(degree)),0,1,speedY*(getSize()),0));
            if(degree++ >= 90){
                degree = 0;
                move = false;
            }
        }
        if(getX() > Frame.WIDTH-size){
            Shredder.soundPool.play(Shredder.bounceID, 0.08f, 0.08f, 1, 0, (float)(Calc.map(this.getSize(),0,500,1f,0f)));
            setX(Frame.WIDTH - size);
            speedX*=-1;
        }
        else{
            if(getX() < 0){
                Shredder.soundPool.play(Shredder.bounceID, 0.08f, 0.08f, 1, 0, (float)(Calc.map(this.getSize(),0,500,1f,0f)));
                setX(0);
                speedX*=-1;
            }
        }

        if(getY()+size > Frame.HEIGHT){
            Shredder.soundPool.play(Shredder.bounceID, 0.08f, 0.08f, 1, 0, (float)(Calc.map(this.getSize(),0,500,1f,0f)));
            setY(Frame.HEIGHT - size);
            speedY*=-1;
        }
        else{
            if(getY() < 0){
                Shredder.soundPool.play(Shredder.bounceID, 0.08f, 0.08f, 1, 0, (float)(Calc.map(this.getSize(),0,500,1f,0f)));
                setY(0);
                speedY*=-1;
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.save();
        canvas.rotate(rotation, rect.centerX(), rect.centerY());
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
        canvas.restore();
    }

    @Override
    public boolean containsMouse(){
        if(rect.contains(Frame.p.x,Frame.p.y)){
            return true;
        }else return false;
    }

    public int getX() {
        return (int) rect.centerX()-size/2;
    }

    public void setX(double d) {
        rect.set((int)d,getY(),(int)d+size,getY()+size);
    }

    public int getY() {
        return (int) rect.centerY()-size/2;
    }

    public void setY(double d) {
        rect.set(getX(),(int)d,getX()+size,(int)d + size);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void cloneItself() {
        for (int i = 0; i < Calc.random.nextInt(2)+3; i++) {
            new Square(getX()+super.getSize()/2, getY()+super.getSize()/2, super.getSize()/2, getColor(), true);
        }
        //new Square(getX()+super.getSize()/2, getY()+super.getSize()/2, super.getSize()/2, getColor(), true);
        //new Square(getX()+super.getSize()/4, getY()+super.getSize()/4, super.getSize()/2, getColor(),true);
    }

}
