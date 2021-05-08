package com.jony.farm.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.combodia.basemodule.utils.LogUtils;
import com.jony.farm.R;

import java.util.ArrayList;

/**
 * Author:ganzhe
 * 时间:2021/4/22 13:02
 * 描述:This is LuckDrawView
 */
public class LuckDrawView extends View {
    private Paint mPaintArc;//转盘扇形画笔
    private Paint mPaintLine;//转盘扇形画笔
    private float mRadius;//圆盘的半径
    private RectF rectFPan;//构建转盘的矩形

    private Paint mPaintItemStr;//转盘文字画笔
    private ArrayList<Path> mArcPaths;
    private RectF rectFStr;//构建文字圆盘的矩形
    private float mTextSize = 20;//文字大小

    private int mRepeatCount = 4;//转几圈
    private int mLuckNum = 2;//最终停止的位置
    private float mStartAngle = 0;//存储圆盘开始的位置
    private float mOffsetAngle = 0;//圆盘偏移角度（当Item数量为4的倍数的时候）
    private ObjectAnimator objectAnimator;
    private LuckPanAnimEndCallBack luckPanAnimEndCallBack;

    public LuckPanAnimEndCallBack getLuckPanAnimEndCallBack() {
        return luckPanAnimEndCallBack;
    }

    public void setLuckPanAnimEndCallBack(LuckPanAnimEndCallBack luckPanAnimEndCallBack) {
        this.luckPanAnimEndCallBack = luckPanAnimEndCallBack;
    }

    private float mItemAnge;
    private String[] mItemStrs = {"0$","1$","2$","3$","4$","5$","6$","7$"};


    public LuckDrawView(Context context) {
        this(context,null);
    }

    public LuckDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuckDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        mPaintArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintArc.setStyle(Paint.Style.FILL);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStyle(Paint.Style.FILL);

        mPaintItemStr = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintItemStr.setColor(Color.parseColor("#ED2F2F"));//设置文字颜色
        mPaintItemStr.setStrokeWidth(3);
        mPaintItemStr.setTextAlign(Paint.Align.CENTER);//设置文字水平居中对齐
        mArcPaths = new ArrayList<>();
    }

    /**
     * 设置转盘数据
     */
    public void setLuckNumber(int luckNumber){
        mLuckNum = luckNumber;
    }
    /**
     * 设置转盘数据
     * @param items
     */
    public void setItems(String[] items){
        mItemStrs = items;

        invalidate();
    }

    public void startAnim(){
        if(objectAnimator!=null){
            objectAnimator.cancel();
        }

        float v = mItemAnge*mLuckNum+mStartAngle%360;//如果转过一次了那下次旋转的角度就需要减去上一次多出的，否则结束的位置会不断增加的
        objectAnimator = ObjectAnimator.ofFloat(this, "rotation", mStartAngle,mStartAngle-mRepeatCount*360-v);
      //  objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 360,410);
        objectAnimator.setDuration(4000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(luckPanAnimEndCallBack!=null){
                    luckPanAnimEndCallBack.onAnimEnd(mItemStrs[mLuckNum]);
                }
            }
        });
        objectAnimator.start();
        mStartAngle -= mRepeatCount*360+v;//将上一次的角度加进来以达到下次开始就是上次结束的位置
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtils.error("w:"+w+"h:"+h+"oldw:"+oldw+"oldh:"+oldh);
        mRadius = Math.min(w,h)/2;//*0.9f;
        //这里是将（0，0）点作为圆心
        rectFPan = new RectF(-mRadius,-mRadius,mRadius,mRadius);
        //每一个Item的角度
        mItemAnge = 360 / mItemStrs.length;

        rectFStr = new RectF(-mRadius/7*5,-mRadius/7*5,mRadius/7*5,mRadius/7*5);//构建文字路径的矩形半径为圆盘的五分之七
        mTextSize = mRadius/9;//根据圆盘的半径设置文字大小
        mPaintItemStr.setTextSize(mTextSize);//设置文字大小

        //数据初始化
        mOffsetAngle=0;
        mStartAngle=0;
        mOffsetAngle = mItemAnge/2;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate((float) getWidth()/2,(float) getHeight()/2);//为了操作方便将画布中心点设置为（0，0）
        canvas.rotate(-90-mOffsetAngle);//将画布旋转（-90-mOffsetAngle）度
       //  Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_zhuan01);
       //  Rect desRetc =  new Rect(-(int) mRadius*3/2,-(int) mRadius*3/2,(int) mRadius*3/2,(int) mRadius*3/2);
      //   canvas.drawBitmap(bgBitmap,null,desRetc,null);


        drawPanItem(canvas);//画转盘
        drawText(canvas);
    }

    //画文字
    private void drawText(Canvas canvas) {
        for(int x = 0;x<mItemStrs.length;x++){
            Path path = mArcPaths.get(x);
            canvas.drawTextOnPath(mItemStrs[x],path,0,0,mPaintItemStr);
        }
    }

    private void drawPanItem(Canvas canvas) {
        float startAng = 0;//扇形开始的角度
        for (int x = 0;x< mItemStrs.length;x++){

            mPaintArc.setColor(Color.parseColor("#feec91"));

            //以下是添加文字绘制路径的代码
            Path path = new Path();
            path.addArc(rectFStr,startAng,mItemAnge);//文字的路径圆形比盘的小
            mArcPaths.add(path);
            //==========================
            canvas.drawArc(rectFPan,startAng,mItemAnge,true,mPaintArc);//画扇形

            startAng+=mItemAnge;//每画完一次增加开始角度
        }
        //画线
        mPaintLine.setColor(Color.parseColor("#f8d17a"));
        for (int x=0;x<mItemStrs.length;x++){
            float arc = x*mItemAnge;
            canvas.drawLine(0,0,((float) Math.cos(arc/180* Math.PI))*mRadius,((float) Math.sin(arc/180*Math.PI))*mRadius,mPaintLine);
        }



    }
}
