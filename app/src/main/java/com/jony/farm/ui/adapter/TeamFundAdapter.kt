package com.jony.farm.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.TeamTrade
import com.jony.farm.model.entity.TeamTradeEntity

/**
 *Author:ganzhe
 *时间:2021/5/10 19:16
 *描述:This is TeamFundAdapter
 */
class TeamFundAdapter:BaseQuickAdapter<TeamTradeEntity,BaseViewHolder>(R.layout.item_teamfund),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TeamTradeEntity) {
        val tv_type = holder.getView<TextView>(R.id.tv_type)
        val tv_name = holder.getView<TextView>(R.id.tv_name)
        val tv_time = holder.getView<TextView>(R.id.tv_time)
        val tv_money = holder.getView<TextView>(R.id.tv_money)

        tv_name.text = TeamTrade.getTradeName(item.tradeItem)
        tv_time.text = item.addTime
        tv_money.text = item.tradeFee.toString()

    }
}