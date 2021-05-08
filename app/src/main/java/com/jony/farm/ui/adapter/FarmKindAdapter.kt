package com.jony.farm.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.KindEntity
import com.jony.farm.util.CommonUtil

/**
 *Author:ganzhe
 *时间:2021/4/18 20:28
 *描述:This is FarmKindAdapter
 */
class FarmKindAdapter: BaseQuickAdapter<KindEntity, BaseViewHolder>(R.layout.item_farm_animalkind){

    override fun convert(holder: BaseViewHolder, item: KindEntity) {
        val iv = holder.getView<ImageView>(R.id.iv_farm_animal_kind)
        val resId = CommonUtil.getKindImgByAnimalId(item.animalID)
        iv.setImageResource(resId)
    }
}