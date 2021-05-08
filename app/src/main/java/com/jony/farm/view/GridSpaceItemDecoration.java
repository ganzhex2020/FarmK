package com.jony.farm.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:ganzhe
 * 时间:2020/11/26 16:16
 * 描述:This is GridSpaceItemDecoration
 */
public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    //   private int mSpanCount;//横条目数量
    private int mRowSpacing;//行间距
    private int mColumnSpacing;// 列间距
    private int mSpanCount;

    /**
     * @param rowSpacing    行间距
     * @param columnSpacing 列间距
     */
    public GridSpaceItemDecoration(int rowSpacing, int columnSpacing) {
        //  this.mSpanCount = spanCount;
        this.mRowSpacing = rowSpacing;
        this.mColumnSpacing = columnSpacing;
    }

    /**
     * 是否是最后一行
     */
    private boolean isLastRow(int itemPosition, RecyclerView parent) {

        int childCount = parent.getAdapter().getItemCount();
        double count = Math.ceil((double) childCount / (double) mSpanCount);//总行数
        double currentCount = Math.ceil((double) (itemPosition + 1) / mSpanCount);//当前行数

        //最后当前数量小于总的
        if (currentCount < count) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否是最后一列
     */
    private boolean isLastColum(int itemPosition, RecyclerView parent) {

        if ((itemPosition + 1) % mSpanCount == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //   GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        //    int childSize = parent.getChildCount();
//        int mSpanCount = manager.getSpanCount();
//        int position = parent.getChildAdapterPosition(view); // 获取view 在adapter中的位置。
//        int column = position % mSpanCount; // view 所在的列

        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view); // 获取view 在adapter中的位置。
        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        int span = manager.getSpanCount();
        int spanSize = manager.getSpanSizeLookup().getSpanSize(position);
        mSpanCount = span / spanSize; //列数
        int column = position % mSpanCount; // view 所在的列

        //第一列 设置右边间距
        if (column == 0) {
            outRect.right = mColumnSpacing / 2;
        }

        //最后一列 设置左边间距
        if (column == mSpanCount - 1) {
            outRect.left = mColumnSpacing / 2;
        }

        //不是第一列 也不是最后一列 设置左右间距
        if (column != 0 && column != mSpanCount - 1) {
            // 左右间距
            outRect.left = mColumnSpacing / 2;
            outRect.right = mColumnSpacing / 2;
        }

        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing
/*        if (position >= mSpanCount) {
            outRect.top = mRowSpacing; // item top
        }*/

        outRect.top = mRowSpacing / 2;
        outRect.bottom = mRowSpacing / 2;
    }


}
