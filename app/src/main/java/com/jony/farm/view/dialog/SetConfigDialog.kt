package com.jony.farm.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.jony.farm.R
import com.jony.farm.util.DeviceUtil
import kotlinx.android.synthetic.main.dialog_setconfig.*

class SetConfigDialog(context: Context, title: String, content: String) : Dialog(
    context,
    R.style.dialog_center_full
) {

    init {
        setCanceledOnTouchOutside(true)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        val view = View.inflate(context, R.layout.dialog_setconfig, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 80f),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        tv_title.text = title
        et_content.setText(content)
        btnSure.setOnClickListener {
            val content = et_content.text.toString().trim()
            if (content.isNotBlank()){
                mOnClickListener?.invoke(content)
                dismiss()
            }
        }
        btnCancel.setOnClickListener {
            dismiss()
        }

    }

    var mOnClickListener: ((str:String) -> Unit)? = null
    fun setOnClickListener(l: (str:String) -> Unit) {
        mOnClickListener = l
    }
}