package com.jony.farm.ui.adapter

import android.annotation.SuppressLint
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.AnimalKindType
import com.jony.farm.model.entity.KindEntity
import com.jony.farm.util.*

/**
 *Author:ganzhe
 *时间:2021/3/11 11:48
 *描述:This is HomeAdapter
 */
class MarketAdapter :BaseQuickAdapter<KindEntity,BaseViewHolder>(R.layout.home_recy_item){
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
        val tv_fodder = holder.getView<TextView>(R.id.tv_fodder)
        val tv_sale = holder.getView<TextView>(R.id.tv_sale)
        val iv_soldout = holder.getView<ImageView>(R.id.iv_soldout)
        val fl_jjkf = holder.getView<FrameLayout>(R.id.fl_jjkf)
        val ll_animal = holder.getView<LinearLayout>(R.id.ll_animal)
        val tv_rushbuy = holder.getView<TextView>(R.id.tv_rushbuy)
        /*
        val tv_supply = holder.getView<TextView>(R.id.tv_supply)
        */
        val resId = CommonUtil.getImgByAnimalId(item.animalID)
        iv_animal.setImageResource(resId)
        tv_name.text = CommonUtil.getGameName(item.animalName)//context.getString(AnimalKindType.getAnimalLabel(item.animalID))//item.animalName
        tv_price.text = "${item.price}"
        tv_profit.text = String.format(context.getString(R.string.market_profit_content),item.profitRate.toString()+"%/",item.cycleDay,
            MathUtil.getTwoBigDecimal(item.profitRate*item.price/item.cycleDay))//"${item.profitRate}%/${item.cycleDay}Days (${MathUtil.getTwoBigDecimal(item.profitRate*item.price/item.cycleDay)} per day)"
        tv_fodder.text = String.format(context.getString(R.string.market_perday),item.needFodder.toString())//item.needFodder.toString()
        tv_sale.text = item.buyStartTime.take(5)+"~"+item.buyEndTime.take(5)
        /*
        tv_supply.text = String.format(context.getString(R.string.market_perday,item.sellCount.toString()))//item.sellCount.toString()*/
        if(item.animalID<1||item.animalID>8){
            ll_animal.gone()
            fl_jjkf.visible()
        }else{
            ll_animal.visible()
            fl_jjkf.gone()
            when(item.saleState){
                1 ->{
                    tv_rushbuy.visible()
                    iv_soldout.gone()
                }
                2 ->{
                    tv_rushbuy.invisible()
                    iv_soldout.gone()
                }
                3 ->{
                    tv_rushbuy.invisible()
                    iv_soldout.visible()
                }
            }
        }



    }
}