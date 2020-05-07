package com.mod.cube;

import android.graphics.Canvas;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by özkan in 8.01.2017.
 */

public class MainThread extends  Thread {

    public static final int MAX_FPS = 60;
    public static double averageFPS;
    private SurfaceHolder surfaceHolder;
    private Frame frame;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, Frame frame){
        super();
        this.surfaceHolder = surfaceHolder;
        this.frame = frame;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.frame.update();
                    this.frame.draw(canvas);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try {
                if(waitTime > 0){
                    this.sleep(waitTime);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MAX_FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                Log.e("FrameCount: ",averageFPS+"");
            }
        }
    }

}
