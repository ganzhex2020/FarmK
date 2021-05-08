package com.combodia.basemodule.common.callback

import com.combodia.basemodule.R
import com.kingja.loadsir.callback.Callback

/**
 *Author:ganzhe
 *时间:2020/10/23 19:51
 *描述:This is LoadingCallBack
 */
class LoadingCallBack:Callback() {

    override fun onCreateView(): Int = R.layout.layout_loading

}