package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.jony.farm.R
import com.jony.farm.viewmodel.RegisterViewModel
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/1 20:33
 *描述:This is RegisterActivity
 */
class RegisterActivity:BaseVMActivity<RegisterViewModel>() {

    override fun initVM(): RegisterViewModel = getViewModel()
    
    override fun getLayoutResId(): Int = R.layout.activity_register

    override fun initView() {

    }

    override fun initData() {

    }

    override fun startObserve() {

    }
}