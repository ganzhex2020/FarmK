package com.jony.farm.ui.adapter

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.utils.GlideUtils
import com.jony.farm.R
import com.jony.farm.model.entity.RankEntity

/**
 *Author:ganzhe
 *时间:2021/5/17 21:07
 *描述:This is RankAdapter
 */
class RankAdapter:BaseQuickAdapter<RankEntity,BaseViewHolder>(R.layout.item_rank),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: RankEntity) {

        val tv_num = holder.getView<TextView>(R.id.tv_num)
        val iv_rank = holder.getView<ImageView>(R.id.iv_rank)
        val tv_rank_name = holder.getView<TextView>(R.id.tv_rank_name)
        val tv_rank_amount = holder.getView<TextView>(R.id.tv_rank_amount)

        when(holder.layoutPosition){
            0 ->{
                tv_num.text = ""
                tv_num.setBackgroundResource(R.mipmap.ic_hg04)
            }
            1 ->{
                tv_num.text = ""
                tv_num.setBackgroundResource(R.mipmap.ic_hg05)
            }
            else ->{
                tv_num.text = (holder.layoutPosition+4).toString()
                tv_num.setTextColor(Color.parseColor("#dba583"))
                tv_num.background = null
            }
        }
        GlideUtils.loadAvatar(iv_rank,item.headImg,R.mipmap.ic_avatar_default)
        tv_rank_name.text = item.showName
        tv_rank_amount.text = item.tradeAmount.toString()+" $"

    }
}