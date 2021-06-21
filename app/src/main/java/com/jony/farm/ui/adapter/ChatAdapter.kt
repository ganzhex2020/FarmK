package com.jony.farm.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.utils.GlideUtils
import com.combodia.basemodule.view.CircleImageView
import com.jony.farm.R
import com.jony.farm.model.entity.SocketMsg
import com.jony.farm.ui.activity.CommunityActivity

class ChatAdapter constructor(list:MutableList<SocketMsg>): BaseMultiItemQuickAdapter<SocketMsg,BaseViewHolder>(list){
    init{
        addItemType(CommunityActivity.LEFT, R.layout.item_chat_left)
        addItemType(CommunityActivity.RIGHT,R.layout.item_chat_right)
    }

    override fun convert(holder: BaseViewHolder, item: SocketMsg) {
        val iv_avatar = holder.getView<CircleImageView>(R.id.iv_avatar)
        val tv_name = holder.getView<TextView>(R.id.tv_name)
        val tv_content = holder.getView<TextView>(R.id.tv_content)
        val tv_time = holder.getView<TextView>(R.id.tv_time)

        GlideUtils.loadAvatar(iv_avatar,item.msgBean.headImg?:"",R.mipmap.ic_avatar_default)
        tv_name.text = item.msgBean.name
        tv_content.text = item.msgBean.content
        tv_time.text = item.msgBean.betTime
    }
}