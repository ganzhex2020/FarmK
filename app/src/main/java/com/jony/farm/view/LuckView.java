package com.jony.farm.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import com.jony.farm.R;

public class LuckView extends View {
    private static final String TAG = "LuckView";

    private Paint mPaintArc;//转盘扇形画笔
    private Paint divisionPaint;//转盘扇形分割线画笔
    private int mRadius;//圆盘的半径
    private RectF rectFPan;//构建转盘的矩形
    private int widthSize;
    private int heightSize;
    private float cX;
    private float cY;

    private int mRepeatCount = 6;//转几圈
    private int mLuckNum = 2;//最终停止的位置
    private float mStartAngle = 0;//存储圆盘开始的位置
    private ObjectAnimator objectAnimator;
    private LuckPanAnimEndCallBack luckPanAnimEndCallBack;

    public LuckPanAnimEndCallBack getLuckPanAnimEndCallBack() {
        return luckPanAnimEndCallBack;
    }

    public void setLuckPanAnimEndCallBack(LuckPanAnimEndCallBack luckPanAnimEndCallBack) {
        this.luckPanAnimEndCallBack = luckPanAnimEndCallBack;
    }

    private float mItemAnge;
    private int[] mItemRes = {R.drawable.ic_luckdraw_bg1, R.drawable.ic_luckdraw_bg2, R.drawable.ic_luckdraw_bg3, R.drawable.ic_luckdraw_bg4, R.drawable.ic_luckdraw_bg5, R.drawable.ic_luckdraw_bg6, R.drawable.ic_luckdraw_bg7, R.drawable.ic_luckdraw_bg8};


    public LuckView(Context context) {
        super(context);
        init();
    }

    public LuckView(Context context, @Nullable  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LuckView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 设置转盘数据
     */
    public void setLuckNumber(int luckNumber) {
        mLuckNum = luckNumber;
    }

    /**
     * 设置转盘数据
     *
     * @param items
     */
    public void setItems(int[] items) {
        mItemRes = items;
        invalidate();
    }

    private void init(){
        mPaintArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintArc.setStyle(Paint.Style.FILL);

        divisionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        divisionPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG,"onMeasure"+" width:"+widthSize+" height:"+heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG,"onSizeChanged "+"w:"+w+" h:"+h+" oldw:"+oldw+" oldh:"+oldh);
        mRadius = Math.min(w, h) / 2;
        cX = mRadius;
        cY = mRadius;
        //这里是将（0，0）点作为圆心
       // rectFPan = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        mItemAnge = 360/mItemRes.length;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG,"onLayout "+" changed:"+changed+"left:"+left+" top:"+top+" right:"+right+" bottom:"+bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG,"onDraw");
      //  canvas.translate((float) getWidth() / 2, (float) getHeight() / 2);//为了操作方便将画布中心点设置为（0，0）
      //  drawPanItem(canvas);
        canvas.rotate(-90 - mItemAnge/2,cX, cY);//将画布旋转（-90-mOffsetAngle）度

        RectF f = new RectF(0, 0, 2 * mRadius, 2 * mRadius);
        for (int i=0;i<mItemRes.length;i++){
            mPaintArc.setColor(Color.parseColor("#feec91"));
            divisionPaint.setColor(Color.parseColor("#f8d17a"));
            // 设置绘制图片的区域
            Rect rect = new Rect((int) (cX + mRadius/2-20),
                    (int)(cY + Math.sin(180/mItemRes.length * Math.PI / 180) + 5),
                    (int) (cX + mRadius /2 + 80),
                    (int)(cY + Math.sin(180/mItemRes.length * Math.PI / 180) + 105));
            //Rect rect = new Rect((int) (mRadius*0.2),10,(int) (mRadius*0.9),110);

            canvas.drawArc(f, 0, mItemAnge, true, mPaintArc);
            canvas.drawLine(cX, cY, cX + mRadius, cY, divisionPaint);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mItemRes[i]);
            bitmap = rotateBitmap(bitmap,20f);
            canvas.drawBitmap(bitmap, null, rect, null);
//            divisionPaint.setColor(Color.parseColor("#ff0000"));
//            canvas.drawRect(rect,divisionPaint);

            canvas.rotate(mItemAnge, cX, cY);
        }
    }


    public void startGo(){
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        mStartAngle = 0;
        float v = (360-mItemAnge * mLuckNum) + mStartAngle % 360;
        objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, mStartAngle +mRepeatCount * 360 +v);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(5000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (luckPanAnimEndCallBack != null) {
                    luckPanAnimEndCallBack.onAnimEnd( mLuckNum);
                }
            }
        });
        objectAnimator.start();
      //  mStartAngle += mRepeatCount * 360 + v;//将上一次的角度加进来以达到下次开始就是上次结束的位置

    }

    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }


}
