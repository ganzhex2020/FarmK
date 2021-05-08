package com.jony.farm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.combodia.basemodule.utils.LogUtils;

/**
 * Author:ganzhe
 * 时间:2021/4/19 14:30
 * 描述:This is MyLingxing
 */
public class MyCusFarmView extends View {

    private Paint mPaint;
    //private Canvas mCanvas;
    private Path mPath;


    public MyCusFarmView(Context context) {
        this(context,null);
    }

    public MyCusFarmView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCusFarmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(/*Paint.ANTI_ALIAS_FLAG*/);
        //mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.error("宽度:"+getWidth()+"高度:"+getHeight());
        mPath.moveTo(0,getHeight()/2);
        mPath.lineTo(getWidth()/2,0);
        mPath.lineTo(getWidth(),getHeight()/2);
        mPath.lineTo(getWidth()/2,getHeight());
        canvas.drawPath(mPath,mPaint);
    }
}
