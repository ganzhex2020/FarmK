package com.jony.farm.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.AccountDetailEntity
import com.jony.farm.model.entity.TradItem
import com.jony.farm.model.entity.TradState
import com.jony.farm.model.entity.TradType
import com.jony.farm.util.visible

/**
 *Author:ganzhe
 *时间:2021/5/4 14:35
 *描述:This is AccountDetailAdapter
 */
class FodderDetailAdapter:BaseQuickAdapter<AccountDetailEntity,BaseViewHolder>(R.layout.item_gcdetail),LoadMoreModule {
    /*companion object{
        var type = 0
    }*/

    override fun convert(holder: BaseViewHolder, item: AccountDetailEntity) {
        val iv_type = holder.getView<ImageView>(R.id.iv_type)
        val tv_type = holder.getView<TextView>(R.id.tv_type)
        val tv_name = holder.getView<TextView>(R.id.tv_name)
        val tv_time = holder.getView<TextView>(R.id.tv_time)
        val tv_money = holder.getView<TextView>(R.id.tv_money)
        val tv_status = holder.getView<TextView>(R.id.tv_status)

       /* when(type){
            0 ->{
                tv_type.text = context.getString(R.string.fund_gc)
                iv_type.background = ContextCompat.getDrawable(context,R.drawable.bg_cricle_fundadd)
                if (item.tradeAmount>0){
                    iv_type.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_funddetail_gcz))
                }else{
                    iv_type.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_funddetail_gcf))
                }
            }
            1->{
                tv_type.text = context.getString(R.string.fund_gc)
                iv_type.background = ContextCompat.getDrawable(context,R.drawable.bg_cricle_fundadd)
                iv_type.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_funddetail_gcz))
            }
            2->{

            }
        }*/
        tv_type.text = context.getString(R.string.fund_fodder)
        iv_type.background = ContextCompat.getDrawable(context,R.drawable.bg_cricle_fundadd)
        if (item.tradeAmount>0){
            iv_type.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_funddetail_slz))
        }else{
            iv_type.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_funddetail_slf))
        }
        tv_time.text = item.addTime
        tv_money.text = item.tradeAmount.toString()
        try {
            tv_name.text = context.getString(TradType.getTradLabel(item.tradeType))
        }catch (e:Exception){

        }


    }
}