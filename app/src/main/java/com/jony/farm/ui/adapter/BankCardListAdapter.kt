package com.jony.farm.ui.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.ext.visable
import com.combodia.basemodule.utils.GlideUtils
import com.jony.farm.R
import com.jony.farm.model.entity.BankCardEntity

/**
 *Author:ganzhe
 *时间:2021/4/26 20:52
 *描述:This is BankCardListAdapter
 */
class BankCardListAdapter:BaseQuickAdapter<BankCardEntity, BaseViewHolder>(R.layout.item_bankcard) {
    override fun convert(holder: BaseViewHolder, item: BankCardEntity) {
        val cl_parent = holder.getView<ConstraintLayout>(R.id.cl_parent)
        val iv_bankcard_logo = holder.getView<ImageView>(R.id.iv_bankcard_logo)
        val tv_bank_name = holder.getView<TextView>(R.id.tv_bank_name)
        val tv_bankcard_account = holder.getView<TextView>(R.id.tv_bankcard_account)
        val tv_set_default= holder.getView<TextView>(R.id.tv_set_default)

        tv_bank_name.text = item.bankName
        if(item.bankNumber!=null){
            tv_bankcard_account.text = "**** **** **** "+item.bankNumber.takeLast(4)
        }
        tv_set_default.visable(!item.isDefault)
     //   GlideUtils.loadCircleImage(iv_bankcard_logo, item.disLogo)

       /* Glide.with(cl_parent.context).load(item.disBack).into(object : SimpleTarget<Drawable?>() {

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                cl_parent.background = resource
            }
        })*/

    }
}