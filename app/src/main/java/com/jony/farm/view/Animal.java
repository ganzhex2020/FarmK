package com.jony.farm.view;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Animal {
    int locationX = 0;
    int locationY = 0;
    int frameIndex = 0;

    int animalId;
    int cycleDay;
    double profitRate;
    int needFodder;
    boolean needFeedToday;
    int state;


    public void swing(Canvas canvas, Paint paint){
        canvas.drawBitmap(GameConfig.animalFrame[frameIndex],locationX,locationY,paint);
        frameIndex = (++frameIndex)%3;
    }
}
