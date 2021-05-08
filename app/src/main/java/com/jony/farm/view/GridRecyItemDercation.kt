package com.jony.farm.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridRecyItemDercation(
    private val itemSpaceLeft:Int,
    private val itemSpaceCenter:Int,
    private val itemSpaceTop:Int,
    private val itemSpaceBottom:Int,
    private val itemNum:Int):
    RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
      //  super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (parent.childCount > 0) {
            if (position % itemNum == 0) { //最左边Item
                outRect.left = itemSpaceLeft
                outRect.right = itemSpaceCenter / 2
            } else if (position % itemNum == itemNum - 1) { //最右边Item
                outRect.left = itemSpaceCenter / 2
                outRect.right = itemSpaceLeft
            } else { //中间Item
                outRect.left = itemSpaceCenter / 4
                outRect.right = itemSpaceCenter / 4
            }
            outRect.top = itemSpaceTop
            outRect.bottom = itemSpaceBottom
        }

    }
}