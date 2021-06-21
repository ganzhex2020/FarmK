package com.jony.farm.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.text.Html
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.ext.color
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.ext.visable
import com.combodia.httplib.config.Constant
import com.jony.farm.R
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.model.entity.QueuEntity
import com.jony.farm.util.DeviceUtil
import com.jony.farm.viewmodel.FarmViewModel
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.dialog_feedall.*
import kotlinx.android.synthetic.main.dialog_queuel.*


/**
 *Author:ganzhe
 *时间:2020/12/25 11:09
 *描述:This is VersionUpdateDialog
 */
class QueueDialog(context: Context, queuEntity: QueuEntity) :
        Dialog(
                context,
                R.style.dialog_center_full
        ) {



    init {

        setCanceledOnTouchOutside(true)
        setCanceledOnTouchOutside(true)
        val view = View.inflate(context, R.layout.dialog_queuel, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
                DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 20f),
                ViewGroup.LayoutParams.WRAP_CONTENT
        )

        tv_count.text = Html.fromHtml(String.format(context.getString(R.string.farm_gather_ahead),queuEntity.aheadCount.toString()))//queuEntity.aheadCount.toString()
        tv_minutes.text = Html.fromHtml(String.format(context.getString(R.string.farm_gather_minute),queuEntity.needSecondes.toString()))//queuEntity.needSecondes.toString()

        iv_cancle.setOnClickListener {
            dismiss()
        }
        iv_confirm.setOnClickListener {
            mOnClickListener?.invoke()
            dismiss()
        }

    }


    var mOnClickListener: (() -> Unit)? = null
    fun setOnClickListener(l: () -> Unit) {
        mOnClickListener = l
    }


}