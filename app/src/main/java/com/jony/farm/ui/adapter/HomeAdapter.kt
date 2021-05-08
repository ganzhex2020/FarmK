package com.jony.farm.ui.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.KindEntity
import com.jony.farm.util.CommonUtil
import com.jony.farm.util.MathUtil

/**
 *Author:ganzhe
 *时间:2021/3/11 11:48
 *描述:This is HomeAdapter
 */
class HomeAdapter :BaseQuickAdapter<KindEntity,BaseViewHolder>(R.layout.home_recy_item){
    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: KindEntity) {
       /* val iv = holder.getView<ImageView>(R.id.iv_home_recy_item)
        val tv = holder.getView<TextView>(R.id.tv_home_recy_item)
        val url = "https://cdn.pixabay.com/photo/2013/10/09/02/26/cattle-192976_1280.jpg"
        GlideUtils.loadImage(iv,url)

        tv.rotation = 30f
        tv.text = item*/

        val iv_animal = holder.getView<ImageView>(R.id.iv_animal)
        val tv_name = holder.getView<TextView>(R.id.tv_name)
        val tv_price = holder.getView<TextView>(R.id.tv_price)
        val tv_profit = holder.getView<TextView>(R.id.tv_profit)
        val tv_sale = holder.getView<TextView>(R.id.tv_sale)
        val tv_fodder = holder.getView<TextView>(R.id.tv_fodder)
        val tv_supply = holder.getView<TextView>(R.id.tv_fodder)

        tv_name.text = item.animalName
        tv_price.text = "Price:${item.price}"
        tv_profit.text = "${item.profitRate}%/${item.cycleDay}Days (${MathUtil.getTwoBigDecimal(item.profitRate*item.price/item.cycleDay)} per day)"
        tv_sale.text = item.buyStartTime+"~"+item.buyEndTime
        tv_fodder.text = item.needFodder.toString()+"/Day"
        tv_supply.text = item.sellCount.toString()+"/Day"

        val resId = CommonUtil.getImgByAnimalId(item.animalID)
        iv_animal.setImageResource(resId)

    }
}