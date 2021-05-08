package com.combodia.basemodule.common.callback

import android.content.Context
import android.view.View
import android.widget.TextView
import com.combodia.basemodule.R
import com.combodia.basemodule.utils.LogUtils
import com.kingja.loadsir.callback.Callback

/**
 *Author:ganzhe
 *时间:2020/10/23 19:49
 *描述:This is ErrorPageCallBack
 */
open class ErrorPageCallBack: Callback() {


    override fun onCreateView(): Int = R.layout.layout_error

}