package com.jony.farm.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.jony.farm.R
import com.jony.farm.util.DeviceUtil
import kotlinx.android.synthetic.main.dialog_help.*

class HelpDialog(context:Context) : Dialog(
    context,
    R.style.dialog_center_full
){

    init {

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        val view = View.inflate(context, R.layout.dialog_help, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            //DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 80f),
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        iv_delete.setOnClickListener {
            dismiss()
        }

    }
}