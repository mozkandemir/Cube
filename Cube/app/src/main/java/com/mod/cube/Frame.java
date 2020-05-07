package com.mod.cube;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created in 08.01.2017.
 */
public class Frame extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    public static Point p;
    public static int WIDTH,HEIGHT;
    public static BubblePool bubblePool;
    public Frame(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else {
            display.getSize(size); // correct for devices with hardware navigation buttons
        }

        WIDTH = size.x;
        HEIGHT = size.y;
        p = new Point(200,200);
        /*
        *  DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;

        * */

        new Shredder();

        System.out.println("width: " + WIDTH + " height: " + HEIGHT);

        bubblePool = new BubblePool();
        bubblePool.generateSquare(5,10);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){
                retry = false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                p.set((int)event.getRawX(), (int)event.getRawY());
                Shredder.scan();
                break;
            case MotionEvent.ACTION_DOWN:
                p.set((int)event.getRawX(), (int)event.getRawY());
                Shredder.scan();
                break;
        }
        return true;
    }

    public void update(){
        try {
            for (int i = 0; i < Element.elements.size(); i++) {
                Element.elements.get(i).tick();
            }
        } catch (Exception e) {
            System.err.println("No such element to tick");
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(200,(int)Calc.map(p.y,0,HEIGHT,50,255),(int)Calc.map(p.x,0,WIDTH,50,255)));
        canvas.drawRect(new Rect(0,0,WIDTH,HEIGHT),paint);
        try {
            synchronized (Element.elements) {
                for (int i = Element.elements.size() - 1; i > -1; i--) {
                    Element.elements.get(i).render(canvas);
                }
            }
        } catch (Exception e) {
            System.err.println("No such element to render");
        }
        //paint.setTextSize(paint.getTextSize() * 2);
        //canvas.drawText(MainThread.averageFPS + " FPS ",10,20,paint);
    }

}
