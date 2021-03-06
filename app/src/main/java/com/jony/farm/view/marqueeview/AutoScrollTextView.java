package com.jony.farm.view.marqueeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AutoScrollTextView extends AppCompatTextView {

    private float textLength = 0f;// 文本长度
    private float viewWidth = 0f;
    private float step = 0f;// 文字的横坐标
    private float y = 0f;// 文字的纵坐标
    private float temp_view_plus_text_length = 0.0f;// 用于计算的临时变量
    private float temp_view_plus_two_text_length = 0.0f;// 用于计算的临时变量
    public boolean isStarting = false;// 是否开始滚动
    private Paint paint = null;// 绘图样式
    private CharSequence text = "";// 文本内容
    private float speed = 10f;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public AutoScrollTextView(Context context) {
        super(context);
        initView();
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
//		setOnClickListener(this);
    }

    public void init(float width) {
        text = super.getText();
        paint = super.getPaint();
        // Paint paint=new Paint();
        text = getText().toString();
        textLength = paint.measureText(text.toString());
        // viewWidth = getWidth();
        // if (viewWidth == 0) {
        // if (windowManager != null) {
        // Display display = windowManager.getDefaultDisplay();
        // viewWidth = display.getWidth();
        // }
        // }
        viewWidth = width;
        step = textLength;
        temp_view_plus_text_length = viewWidth + textLength;
        temp_view_plus_two_text_length = viewWidth + textLength * 2;
        y = getTextSize() + getPaddingTop();
        paint.setColor(Color.parseColor("#ff6d2a"));
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.step = step;
        ss.isStarting = isStarting;
        return ss;

    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        step = ss.step;
        isStarting = ss.isStarting;

    }

    public static class SavedState extends BaseSavedState {
        public boolean isStarting = false;
        public float step = 0.0f;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBooleanArray(new boolean[]{isStarting});
            out.writeFloat(step);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
        };

        private SavedState(Parcel in) {
            super(in);
            boolean[] b = new boolean[1];
            if (null != in) {
                in.readBooleanArray(b);
            }
            if (b != null && b.length > 0)
                isStarting = b[0];
            step = in.readFloat();
        }
    }

    public void startScroll() {
        isStarting = true;
        invalidate();
    }

    public void stopScroll() {
        isStarting = false;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        if (null != text && !"".equals(text)) {
            canvas.drawText(text, 0, text.length(), temp_view_plus_text_length
                    - step, y, paint);
            if (!isStarting) {
                return;
            }
            step += speed;
            if (step > temp_view_plus_two_text_length)
                step = textLength;
            invalidate();
        }
    }
}
