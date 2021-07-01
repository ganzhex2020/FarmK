package com.jony.farm.ui.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.ext.visable
import com.combodia.basemodule.utils.GlideUtils
import com.combodia.basemodule.utils.LogUtils
import com.jony.farm.R
import com.jony.farm.model.entity.BankCardEntity
import com.jony.farm.view.ItemTouchStatus

/**
 *Author:ganzhe
 *时间:2021/4/26 20:52
 *描述:This is BankCardListAdapter
 */
class BankCardListAdapter:BaseQuickAdapter<BankCardEntity, BaseViewHolder>(R.layout.item_bankcard),ItemTouchStatus {
    override fun convert(holder: BaseViewHolder, item: BankCardEntity) {
//        val cl_parent = holder.getView<ConstraintLayout>(R.id.cl_parent)
        val iv_bankcard_logo = holder.getView<ImageView>(R.id.iv_bankcard_logo)
        val tv_bank_name = holder.getView<TextView>(R.id.tv_bank_name)
        val tv_bankcard_account = holder.getView<TextView>(R.id.tv_bankcard_account)
      //  val tv_set_default= holder.getView<TextView>(R.id.tv_set_default)

        tv_bank_name.text = item.bankName
        if(item.bankNumber!=null){
            tv_bankcard_account.text = "**** **** **** "+item.bankNumber.takeLast(4)
        }
     //   tv_set_default.visable(!item.isDefault)
     //   GlideUtils.loadCircleImage(iv_bankcard_logo, item.disLogo)

       /* Glide.with(cl_parent.context).load(item.disBack).into(object : SimpleTarget<Drawable?>() {

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                cl_parent.background = resource
            }
        })*/

    }

    override fun onItemDelete(position: Int) {

    }

    override fun onItemRefresh(position: Int) {
        notifyItemChanged(position)
    }

    fun getHolder(rv:RecyclerView,position: Int):RecyclerView.ViewHolder?{
        if (rv?.adapter == null || rv.adapter!!.itemCount==0){
            return null
        }
        val count = rv.adapter!!.itemCount
        if (position<0 || position>count-1){
            return null
        }
        val holder = rv.findViewHolderForAdapterPosition(position)
        if (holder == null){
            val pool = rv.recycledViewPool

        }

        return null

    }
}