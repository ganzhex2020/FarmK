package com.jony.farm.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.NewsEntity

/**
 *Author:ganzhe
 *时间:2021/5/17 19:22
 *描述:This is BlockNewsAdapter
 */
class BlockNewsAdapter:BaseQuickAdapter<NewsEntity,BaseViewHolder>(R.layout.item_blocknews),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: NewsEntity) {

        holder.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_time,item.addTime)
                .setText(R.id.tv_subtitle,item.subTitle)
    }
}