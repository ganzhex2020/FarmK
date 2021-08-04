package com.jony.farm.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.util.CommonUtil

/**
 *Author:ganzhe
 *时间:2021/4/19 15:16
 *描述:This is AnimalAdapter
 */
class AnimalAdapter : BaseQuickAdapter<AnimalEntity, BaseViewHolder>(R.layout.item_farm_animal){

    override fun convert(holder: BaseViewHolder, item: AnimalEntity) {
        val iv = holder.getView<ImageView>(R.id.iv_farm_animal)
        val iv_state = holder.getView<ImageView>(R.id.iv_state)
        iv_state.setImageDrawable(null)
        if (item.animalID!=0){
            val isMature = item.leftSeconde<=0
            val resId = CommonUtil.getFarmGif(item.animalID,isMature)
        //    iv.setImageResource(resId)
            Glide.with(context).asGif().load(resId).into(iv)
            //判断头上显示的状态
            if (item.needFeedToday){
                iv_state.setImageResource(R.mipmap.ic_fantuan)
            }else{
                if(item.leftSeconde<=0){
                    iv_state.setImageResource(R.mipmap.ic_zhaoshou)
                }else{
                    iv_state.setImageDrawable(null)
                }
            }

        }else{
            iv.setImageDrawable(null)
            iv_state.setImageDrawable(null)
        }

    }
}