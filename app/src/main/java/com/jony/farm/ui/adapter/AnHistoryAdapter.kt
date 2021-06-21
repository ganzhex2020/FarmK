package com.jony.farm.ui.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.model.entity.AnimalKindType
import com.jony.farm.util.CommonUtil
import com.jony.farm.util.DateUtil
import com.jony.farm.util.MathUtil

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
        val tv_beginhour = holder.getView<TextView>(R.id.tv_beginhour)
        val tv_endtime = holder.getView<TextView>(R.id.tv_endtime)
        val tv_endhour = holder.getView<TextView>(R.id.tv_endhour)
        val tv_circleday = holder.getView<TextView>(R.id.tv_circleday)
        val tv_lc = holder.getView<TextView>(R.id.tv_lc)
        val tv_rate = holder.getView<TextView>(R.id.tv_rate)
        val tv_state = holder.getView<TextView>(R.id.tv_state)
        iv.setImageResource(CommonUtil.getImgByAnimalId(item.animalID))
        tv_name.text = context.getString(AnimalKindType.getAnimalLabel(item.animalID))//item.animalName
        tv_qc.text = "${item.price} "+context.getString(R.string.fund_gc)
     //   tv_begintime.text = item.buyDate
     //   tv_endtime.text = item.buyDate
        tv_lc.text = "${MathUtil.getTwoBigDecimal(item.price*(1+item.profitRate/100))} "+context.getString(R.string.fund_lc)
        tv_circleday.text = String.format(context.getString(R.string.anihis_days),item.cycleDay.toString())//"${item.cycleDay}Days"
        tv_rate.text = "${item.profitRate}%"

        try {
            tv_begintime.text = DateUtil.beginDate(DateUtil.str2date(item.buyDate)).substring(0,5)
            tv_beginhour.text = DateUtil.beginDate(DateUtil.str2date(item.buyDate)).substring(6)
            tv_endtime.text = DateUtil.dateOff(DateUtil.str2date(item.buyDate),item.cycleDay).substring(0,5)
            tv_endhour.text = DateUtil.dateOff(DateUtil.str2date(item.buyDate),item.cycleDay).substring(6)
        }catch (e :Exception){

        }


        when(item.state){
            0 ->{
                tv_state.text = context.getString(R.string.growing)
                tv_state.background = ContextCompat.getDrawable(context,R.drawable.bg_retc_growing)
            }
            1 ->{
                tv_state.text = context.getString(R.string.waitsale)
                tv_state.background = ContextCompat.getDrawable(context,R.drawable.bg_retc_waiting)
            }
            2 ->{
                tv_state.text = context.getString(R.string.profited)
                tv_state.background = ContextCompat.getDrawable(context,R.drawable.bg_retc_profited)
            }
        }
    }
}