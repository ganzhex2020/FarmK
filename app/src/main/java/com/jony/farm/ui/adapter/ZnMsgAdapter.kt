package com.jony.farm.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.ZnxEntity

class ZnMsgAdapter:BaseQuickAdapter<ZnxEntity,BaseViewHolder>(R.layout.item_znmsg),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ZnxEntity) {
        holder.setText(R.id.tv_title,item.messageTitle)
            .setText(R.id.tv_content,item.messageContent)
            .setText(R.id.tv_time,item.addTime)
        val tv_read = holder.getView<TextView>(R.id.tv_read)
        if (item.readed == 1){
            tv_read.text = context.getString(R.string.msg_read)
        }else{
            tv_read.text = context.getString(R.string.msg_unread)
        }
    }
}