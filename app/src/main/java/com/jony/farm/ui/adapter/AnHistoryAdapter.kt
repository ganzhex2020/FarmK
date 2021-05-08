package com.jony.farm.ui.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.utils.GlideUtils
import com.jony.farm.R
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.util.CommonUtil

/**
 *Author:ganzhe
 *时间:2021/4/28 18:48
 *描述:This is AnHistoryAdapter
 */
class AnHistoryAdapter:BaseQuickAdapter<AnimalEntity,BaseViewHolder>(R.layout.item_animalhistory),LoadMoreModule {

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: AnimalEntity) {
        val iv = holder.getView<ImageView>(R.id.iv_animal)
        val tv_name = holder.getView<TextView>(R.id.tv_name)
        val tv_qc = holder.getView<TextView>(R.id.tv_qc)
        val tv_begintime = holder.getView<TextView>(R.id.tv_begintime)
        val tv_endtime = holder.getView<TextView>(R.id.tv_endtime)
        val tv_circleday = holder.getView<TextView>(R.id.tv_circleday)
        val tv_lc = holder.getView<TextView>(R.id.tv_lc)
        val tv_rate = holder.getView<TextView>(R.id.tv_rate)
        iv.setImageResource(CommonUtil.getImgByAnimalId(item.animalID))
        tv_name.text = item.animalName
        tv_qc.text = item.price.toString()
     //   tv_begintime.text = item.buyDate
     //   tv_endtime.text = item.buyDate
        tv_circleday.text = "${item.cycleDay}Days"
        tv_lc.text = ""
        tv_rate.text = "${item.profitRate}%"
    }
}