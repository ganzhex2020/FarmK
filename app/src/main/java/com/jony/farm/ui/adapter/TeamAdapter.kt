package com.jony.farm.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.TeamMemberEntity
import com.jony.farm.util.MathUtil

/**
 *Author:ganzhe
 *时间:2021/5/4 17:34
 *描述:This is TeamAdapter
 */
class TeamAdapter:BaseQuickAdapter<TeamMemberEntity,BaseViewHolder>(R.layout.item_teammember) ,LoadMoreModule{

    override fun convert(holder: BaseViewHolder, item: TeamMemberEntity) {
        holder.setText(R.id.tv_name,item.userName)
                .setText(R.id.tv_gc,MathUtil.getTwoBigDecimal(item.balance))
                .setText(R.id.tv_lc,MathUtil.getTwoBigDecimal(item.lc))
                .setText(R.id.tv_count,item.layer.toString())
                .setText(R.id.tv_time,item.addTime.take(9))
    }

}