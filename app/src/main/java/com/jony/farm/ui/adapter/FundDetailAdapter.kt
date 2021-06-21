package com.jony.farm.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.AccountDetailEntity
import com.jony.farm.model.entity.TradType
import com.jony.farm.ui.activity.FundDetailActivity

/**
 *Author:ganzhe
 *时间:2021/5/4 14:35
 *描述:This is AccountDetailAdapter
 */
class FundDetailAdapter:BaseQuickAdapter<AccountDetailEntity,BaseViewHolder>(R.layout.item_accountdetail),LoadMoreModule {
    companion object{
        var type = 0
        var tradeType = 1
    }

    override fun convert(holder: BaseViewHolder, item: AccountDetailEntity) {
        val iv_type = holder.getView<ImageView>(R.id.iv_type)
        val tv_type = holder.getView<TextView>(R.id.tv_type)
        val tv_name = holder.getView<TextView>(R.id.tv_name)
        val tv_time = holder.getView<TextView>(R.id.tv_time)
        val tv_money = holder.getView<TextView>(R.id.tv_money)

        when(tradeType){
            1 ->{tv_type.text = context.getString(R.string.fund_gc)}
            2->{tv_type.text = context.getString(R.string.fund_lc)}
            3->{tv_type.text = context.getString(R.string.fund_fodder)}
        }
        tv_name.text = context.getString(TradType.getTradLabel(item.tradeType))
        tv_time.text = item.addTime
        tv_money.text = item.tradeAmount.toString()
    }
}