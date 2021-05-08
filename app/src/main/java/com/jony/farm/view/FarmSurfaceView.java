package com.jony.farm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Author:ganzhe
 * 时间:2021/4/19 13:07
 * 描述:This is FarmSurfaceView
 */
public class FarmSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Context context;
    private Canvas mCanvas;
    private Paint mPaint;
    private SurfaceHolder mHolder;
    private boolean mIsRunning;
    private ArrayList<Animal> layout2;
    Dog dog;

    public FarmSurfaceView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FarmSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        init();
    }

    public FarmSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    private void createElement(){
        layout2 = new ArrayList<>();
        dog = new Dog(GameConfig.animalFrame[0].getWidth(),0);
        layout2.add(dog);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        createElement();
        mIsRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mIsRunning = false;
    }

    @Override
    public void run() {
        while (mIsRunning) {
            synchronized (mHolder) {
                drawBitmap();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void drawBitmap(){
        mCanvas = mHolder.lockCanvas();
        if (mCanvas != null) {
            try {
                int default_color = mPaint.getColor();
                //绘制背景
                mCanvas.drawColor(Color.BLACK);
              //  mCanvas.drawBitmap(GameConfig.gameBG,0,0,mPaint);
                RectF oval2 = new RectF(210,100,250,130);
                //oval2.set(210,100,250,130);
                mCanvas.drawOval(oval2, mPaint);

                for (Animal animal:layout2){
                    animal.swing(mCanvas,mPaint);
                }

                mPaint.setColor(default_color);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
