package com.jony.farm.view;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Author:ganzhe
 * 时间:2021/3/14 20:18
 * 描述:This is Dog
 */
public class Dog extends Animal{
    int frameIndex = 0;



    public Dog(int locationX,int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    @Override
    public void swing(Canvas canvas, Paint paint) {
        canvas.drawBitmap(GameConfig.animalFrame[frameIndex],locationX,locationY,paint);
        frameIndex = (++frameIndex)%3;
    }
}
