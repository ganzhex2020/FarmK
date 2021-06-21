package com.jony.farm.ui.adapter

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.AmountEntity

/**
 *Author:ganzhe
 *时间:2021/4/26 00:28
 *描述:This is AmountAdapter
 */
class AmountAdapter:BaseQuickAdapter<AmountEntity,BaseViewHolder> (R.layout.item_amount){


    override fun convert(holder: BaseViewHolder, item: AmountEntity) {
        val ll_parent = holder.getView<LinearLayout>(R.id.ll_parent)
        val tv_amount = holder.getView<TextView>(R.id.tv_amount)

        tv_amount.text = "${item.amount}"+context.getString(R.string.unit_yuan)
        ll_parent.isSelected = item.isSelect
        if (item.isSelect){
            tv_amount.setTextColor(ContextCompat.getColor(context,R.color.white))
        }else{
            tv_amount.setTextColor(ContextCompat.getColor(context,R.color.color_pay))
        }
    }
}