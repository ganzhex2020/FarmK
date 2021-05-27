package com.jony.farm.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.ext.visable
import com.jony.farm.R
import com.jony.farm.util.DateUtil

/**
 *Author:ganzhe
 *时间:2021/5/18 15:31
 *描述:This is FaqAdapter
 */
class FaqAdapter:BaseQuickAdapter<Int,BaseViewHolder>(R.layout.item_faq),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Int) {
        val iv_show = holder.getView<ImageView>(R.id.iv_show)
        val tv_content = holder.getView<TextView>(R.id.tv_content)

        tv_content.visable(false)

        iv_show.setOnClickListener {
            tv_content.visable(!tv_content.isVisible)
            if(tv_content.isVisible){
                DateUtil.rote(context,1,iv_show)
            }else{
                DateUtil.rote(context,2,iv_show)
            }

        }
    }
}