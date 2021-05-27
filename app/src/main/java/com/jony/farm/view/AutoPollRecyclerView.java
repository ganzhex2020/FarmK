package com.jony.farm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class AutoPollRecyclerView extends RecyclerView {
    //取值  160  2px
    private static final long TIME_AUTO_POLL = 40;//间隔时间
    AutoPollTask autoPollTask;
    private boolean running; //标示是否正在自动轮询
    private boolean canRun;//标示是否可以自动轮询,可在不需要的是否置false

    public AutoPollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addOnScrollListener(mScrollListener);

        autoPollTask = new AutoPollTask(this, 2, 2);
    }


    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoPollRecyclerView> mReference;
        private static int x; //横向偏移量
        private static int y; //纵向偏移量

        public void setTranstion(int transtionX, int transitionY) {
            x = transtionX;
            y = transitionY;
        }

        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(AutoPollRecyclerView reference, int transtionX, int transitionY) {
            this.mReference = new WeakReference<AutoPollRecyclerView>(reference);
            x = transtionX;
            y = transitionY;
        }

        @Override
        public void run() {
            AutoPollRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                recyclerView.scrollBy(x, y);//不论是竖直滚动还是水平滚动，都是偏移2个像素
                recyclerView.postDelayed(recyclerView.autoPollTask, recyclerView.TIME_AUTO_POLL);
            }
        }
    }

    //开启:如果正在运行,先停止->再开启
    public void start() {
        getAdapter().notifyDataSetChanged();
        if (running)
            stop();
        canRun = true;
        running = true;
        postDelayed(autoPollTask, TIME_AUTO_POLL);
    }

    public void stop() {
        running = false;
        removeCallbacks(autoPollTask);
    }


    //控制rv可以滑动
    @Override
    public boolean onTouchEvent(MotionEvent e) {
       /* switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (canRun)
                    start();
                break;
        }
        return super.onTouchEvent(e);*/
        return false; //表示不进行事件拦截 将事件交由父空间进行处理 可以起到屏蔽rv滑动效果
    }

    private OnScrollListener mScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            /*if (!recyclerView.canScrollVertically(1)) { //不能继续向下滑动一个像素了(底部)
                autoPollTask.setTranstion(2,-2);
            } else if (!recyclerView.canScrollVertically(-1)) { //不能继续向上滑动一个像素了(顶部)
                autoPollTask.setTranstion(2,2);
            }*/
            autoPollTask.setTranstion(2,2);
        }
    };

}