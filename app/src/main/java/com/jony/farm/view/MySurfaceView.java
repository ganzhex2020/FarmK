package com.jony.farm.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.jony.farm.R;
import com.jony.farm.util.RouteUtil;

import java.util.ArrayList;

/**
 * Author:ganzhe
 * 时间:2021/3/11 14:26
 * 描述:This is MySurceView
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean mIsRunning;
    private Paint mPaint;
    //private Path mPath;
    private int x = 0, y = 0;
    private int startY = 50, endY = 100;
    private Context context;
//    private Bitmap icon;
//    private int frameIndex = 0;
    private Dog dog,dog1;
    private ArrayList<Animal> layout2;




    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

   /* public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    private void init() {

        mPaint = new Paint();
     //   mPath = new Path();
        mPaint.setColor(Color.BLACK);
      //  mPaint.setStyle(Paint.Style.STROKE);
     //   mPaint.setAntiAlias(true);
     //   mPaint.setStrokeWidth(5);

     //   icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_error);

        //路径起始点(0, 100)
        //  mPath.moveTo(0, 50);

        mHolder = getHolder();
        mHolder.addCallback(this);


    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        createElement();
        mIsRunning = true;
        new Thread(this).start();
    }
    private void createElement(){
        layout2 = new ArrayList<>();
        dog = new Dog(GameConfig.animalFrame[0].getWidth(),0);
        dog1 = new Dog(GameConfig.animalFrame[0].getWidth()*2,0);
        layout2.add(dog);
        layout2.add(dog1);
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
        long start = System.currentTimeMillis();

        while (mIsRunning) {
            synchronized (mHolder) {
                drawBitmap();
            /*    draw();
                startY++;
                endY++;*/
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
              //  mCanvas.drawColor(Color.BLACK);
                mCanvas.drawBitmap(GameConfig.gameBG,0,0,mPaint);

                //绘制路径
             //   mCanvas.drawPath(mPath, mPaint);
             //   mPaint.setColor(Color.RED);
              //  mCanvas.drawBitmap(icon,100,10,mPaint);
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

    private void draw() {
        mCanvas = mHolder.lockCanvas();
        if (mCanvas != null) {
            try {
                //使用获得的Canvas做具体的绘制
                //获得canvas对象
                int color = mPaint.getColor();
                mPaint.setColor(Color.RED);
                //绘制背景
                mCanvas.drawColor(Color.BLACK);
                //绘制路径
               // mCanvas.drawPath(mPath, mPaint);
                mCanvas.drawRect(50,startY,100,endY,mPaint);
                mPaint.setColor(color);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
