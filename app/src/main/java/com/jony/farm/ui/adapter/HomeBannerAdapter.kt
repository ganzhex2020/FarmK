package com.jony.farm.ui.adapter

import com.combodia.basemodule.utils.GlideUtils
import com.jony.farm.R
import com.jony.farm.model.entity.BannerEntity
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 *Author:ganzhe
 *时间:2021/3/11 13:27
 *描述:This is HomeBannerAdapter
 */
class HomeBannerAdapter(dataList:List<BannerEntity>): BannerImageAdapter<BannerEntity>(dataList) {

    override fun onBindView(holder: BannerImageHolder?, data: BannerEntity?, position: Int, size: Int) {
        if (holder?.itemView!=null){
            GlideUtils.loadImage(holder.imageView, R.mipmap.ic_banner)
        }
    }
}