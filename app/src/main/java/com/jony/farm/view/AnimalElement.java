package com.jony.farm.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Author:ganzhe
 * 时间:2021/3/14 15:43
 * 描述:This is AnimalElement
 */
public class AnimalElement {
    private int locationX;
    private int getLocationY;
    private Rect touchArea;


    public AnimalElement(int locationX, int getLocationY) {
        this.locationX = locationX;
        this.getLocationY = getLocationY;
        touchArea = new Rect(locationX,getLocationY,locationX+GameConfig.animalFrame[0].getWidth(),getLocationY+GameConfig.animalFrame[0].getHeight());

    }

    private void drawSelf(Canvas canvas, Paint paint){
        canvas.drawBitmap(GameConfig.animalFrame[0],locationX,getLocationY,paint);
    }

   /* public boolean onTouch(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (touchArea.contains(x,y)){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    locationX = x - GameConfig.animalFrame[0].getWidth()/2;
                    getLocationY = y - GameConfig.animalFrame[0].getHeight()/2;
                    touchArea.offsetTo(locationX,getLocationY);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        }
        return  false;
    }*/
}
