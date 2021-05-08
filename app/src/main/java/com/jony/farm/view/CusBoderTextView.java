package com.jony.farm.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.jony.farm.R;

/**
 * Author:ganzhe
 * 时间:2021/4/25 15:28
 * 描述:This is CusBoderTextView
 */
public class CusBoderTextView extends AppCompatTextView {

    private Paint mPaint;

    //填充色
    private int solidColor = 0;
    //边框色
    private int strokeColor = 0;
    //边框宽度
    private int mStrokeWidth = 0;
    private String mTvStr;


    public CusBoderTextView(@NonNull Context context) {
        super(context);
    }

    public CusBoderTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);

    }

    public CusBoderTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        init(context, attrs, defStyleAttr);
    }

    private void init(Context context,AttributeSet attrs, int defStyleAttr){

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CusBoderTextView, defStyleAttr, 0);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.CusBoderTextView_cbt_stroke_width, 0);
        strokeColor = a.getColor(R.styleable.CusBoderTextView_cbt_stroke_color, 0);
        solidColor = a.getColor(R.styleable.CusBoderTextView_cbt_solid_color, 0);
     //   mTvStr = a.getString(R.styleable.CusBoderTextView_cbt_tv_str);
        a.recycle();

        mPaint = this.getPaint();

     //   this.setText(mTvStr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(strokeColor);

        mPaint.setStrokeWidth(mStrokeWidth);

    }
}
