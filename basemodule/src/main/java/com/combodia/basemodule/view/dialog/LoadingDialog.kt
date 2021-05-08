package com.combodia.basemodule.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.combodia.basemodule.R

/**
 *Author:ganzhe
 *时间:2020/10/23 22:07
 *描述:This is LoadingDialog
 */
class LoadingDialog(context: Context) :
    Dialog(context, R.style.Loading_dialog) {

    init {
        setCanceledOnTouchOutside(false)
        setCancelable(true)
        val view = View.inflate(context, R.layout.dialog_loading, null)
        val ivLoading = view.findViewById<ImageView>(R.id.ivLoading)
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        Glide.with(context).asGif().load(R.mipmap.ic_loading).into(ivLoading)
    }
}