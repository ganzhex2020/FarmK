package com.combodia.basemodule.base



import androidx.multidex.MultiDexApplication
import com.combodia.basemodule.common.callback.EmptyPageCallBack
import com.combodia.basemodule.common.callback.ErrorPageCallBack
import com.combodia.basemodule.common.callback.LoadingCallBack
import com.combodia.basemodule.utils.LogUtils
import com.kingja.loadsir.core.LoadSir

import com.tencent.mmkv.MMKV

open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initMMKV()
        initLoadSir()
    }

    private fun initMMKV(){
        val rootDir = MMKV.initialize(this)
        LogUtils.error("mmkv root: $rootDir")
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorPageCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyPageCallBack())
            .commit()
    }
}