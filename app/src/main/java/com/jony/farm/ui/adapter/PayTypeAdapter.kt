package com.jony.farm.ui.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.PayType
import com.jony.farm.model.entity.PayTypeEntity
import com.jony.farm.util.DeviceUtil

/**
 *Author:ganzhe
 *时间:2021/4/25 20:27
 *描述:This is PayTypeAdapter
 */
class PayTypeAdapter:BaseQuickAdapter<PayTypeEntity,BaseViewHolder>(R.layout.item_paytype) {


    override fun convert(holder: BaseViewHolder, item: PayTypeEntity) {
        val tv_paytype = holder.getView<TextView>(R.id.tv_paytype)
        val view_line = holder.getView<View>(R.id.view_line)
        when (item.payType){
            PayType.PAYONLINE.id ->{tv_paytype.text = context.getString(PayType.PAYONLINE.label)}
            PayType.BANKTRANSFER.id ->{tv_paytype.text = context.getString(PayType.BANKTRANSFER.label)}
        }
      //  view_line.isSelected = item.isSelect
        val params = view_line.layoutParams
        if (item.isSelect){

            params.height = DeviceUtil.dip2px(context,5f)
            view_line.layoutParams = params
            view_line.setBackgroundResource(R.mipmap.paytype_line)
        }else{
            params.height = DeviceUtil.dip2px(context,2f)
            view_line.layoutParams = params
            view_line.background = null
        }
    }
}