package com.jony.farm.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.TeamMemberEntity

/**
 *Author:ganzhe
 *时间:2021/5/4 17:34
 *描述:This is TeamAdapter
 */
class TeamAdapter:BaseQuickAdapter<TeamMemberEntity,BaseViewHolder>(R.layout.item_teammember) ,LoadMoreModule{

    override fun convert(holder: BaseViewHolder, item: TeamMemberEntity) {
        holder.setText(R.id.tv_name,item.UserName)
                .setText(R.id.tv_gc,item.Balance.toString())
                .setText(R.id.tv_lc,item.TeamBalance.toString())
                .setText(R.id.tv_count,item.Layer.toString())
                .setText(R.id.tv_time,item.AddTime)
    }
}