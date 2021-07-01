package com.jony.farm.view;


import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchStatus {
    public void onItemDelete(int position);//用来删除数据
    public void onItemRefresh(int position);//当用户误操作时用来恢复数据
}