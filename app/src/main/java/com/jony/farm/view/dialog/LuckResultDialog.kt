package com.jony.farm.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jony.farm.R
import com.jony.farm.model.entity.LuckResultEntity
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.gone
import com.jony.farm.util.visible
import com.jony.farm.viewmodel.LuckDrawViewModel
import kotlinx.android.synthetic.main.dialog_luckresult.*

class LuckResultDialog(context: Context,private val luckDrawViewModel: LuckDrawViewModel,private val lifecycleOwner: LifecycleOwner): Dialog(
    context,
    R.style.dialog_center_full
) {
    var pageIndex = 1
    init {
        setCanceledOnTouchOutside(true)
        setCanceledOnTouchOutside(true)
        val view = View.inflate(context, R.layout.dialog_luckresult, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 40f),
            DeviceUtil.dip2px(context,450f)
        )

        /*tv_more.setOnClickListener {
            pageIndex ++
            luckDrawViewModel.getLuckResult(pageIndex)
        }*/


        val mAdapter = ResultAdapter()
        recy_luckresult.adapter = mAdapter
        recy_luckresult.layoutManager = LinearLayoutManager(context)
        mAdapter.loadMoreModule.run {
                preLoadNumber = 3
                setOnLoadMoreListener {
                    pageIndex++
                    luckDrawViewModel.getLuckResult(pageIndex)
                }
            }

        luckDrawViewModel.luckResultLiveData.observe(lifecycleOwner,{
            if (pageIndex == 1){
                if (it.isEmpty()){
                  //  ll1.gone()
                 //   ll2.visible()
                    mAdapter.setEmptyView(R.layout.layout_luckdraw_empty)
                }else{
                    mAdapter.setList(it)
                }
            }else{
                mAdapter.addData(it)
            }
            if (it.size<15){
                mAdapter.loadMoreModule.loadMoreEnd()
            }else{
                mAdapter.loadMoreModule.loadMoreComplete()
            }
        })
    }

    class ResultAdapter:BaseQuickAdapter<LuckResultEntity,BaseViewHolder>(R.layout.item_luckresult),LoadMoreModule{
        override fun convert(holder: BaseViewHolder, item: LuckResultEntity) {
            val tv_time = holder.getView<TextView>(R.id.tv_time)
            val iv_result = holder.getView<ImageView>(R.id.iv_result)
            tv_time.text = item.date

            when(item.drawResult){
                1 ->{iv_result.setImageResource(R.drawable.ic_luckdraw_bg1)}
                2 ->{iv_result.setImageResource(R.drawable.ic_luckdraw_bg2)}
                3 ->{iv_result.setImageResource(R.drawable.ic_luckdraw_bg3)}
                4 ->{iv_result.setImageResource(R.drawable.ic_luckdraw_bg4)}
                5 ->{iv_result.setImageResource(R.drawable.ic_luckdraw_bg5)}
                6 ->{iv_result.setImageResource(R.drawable.ic_luckdraw_bg6)}
                7 ->{iv_result.setImageResource(R.drawable.ic_luckdraw_bg7)}
                8 ->{iv_result.setImageResource(R.drawable.ic_luckdraw_bg8)}
            }

        }
    }

}