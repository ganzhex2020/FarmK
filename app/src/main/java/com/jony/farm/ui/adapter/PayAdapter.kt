package com.jony.farm.ui.adapter

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.PayEntity

/**
 *Author:ganzhe
 *时间:2021/4/25 22:18
 *描述:This is PayAdapter
 */
class PayAdapter:BaseQuickAdapter<PayEntity,BaseViewHolder>(R.layout.item_pay) {

    override fun convert(holder: BaseViewHolder, item: PayEntity) {
        val ll_parent = holder.getView<LinearLayout>(R.id.ll_parent)
        val iv_pay = holder.getView<ImageView>(R.id.iv_pay)
        val tv_name = holder.getView<TextView>(R.id.tv_name)
        val tv_amount = holder.getView<TextView>(R.id.tv_amount)
        tv_name.text = item.platName
        tv_amount.text = "(${item.mixAmount}~${item.maxAmount}"
        ll_parent.isSelected = item.isSelect
        iv_pay.setImageResource(R.drawable.ic_svg_alipay)
    }
}