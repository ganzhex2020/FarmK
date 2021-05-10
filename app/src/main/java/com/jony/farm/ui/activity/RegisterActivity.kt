package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*
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
        immersionBar {
            statusBarColor(R.color.transparent)
        }

        onClick()
    }

    private fun onClick(){
        iv_back.setOnClickListener {
            onBackPressed()
        }
        iv_register.setOnClickListener {
            register()
        }

    }

    private fun register(){
        val username = et_username.text.toString().trim()
        val passwd = et_passwd.text.toString().trim()
        val repasswd = et_repasswd.text.toString().trim()
        val invitecode = et_invitecode.text.toString().trim()

        if (username.isBlank()){
            toast("请输入用户名")
            return
        }
        if (passwd.isBlank()){
            toast("请输入密码")
            return
        }
        if (passwd != repasswd){
            toast("两次输入密码不一致")
            return
        }
        val map = HashMap<String,Any>()
        map["lid"] = invitecode
        map["aid"] = 0
        map["uType"] = 1
        map["userName"] = username
        map["password"] = passwd
        map["appID"] = Const.APP_ID
        map["safeLevel"] = 2

        mViewModel.register(map)
    }

    override fun initData() {

    }

    override fun startObserve() {
        mViewModel.regisLiveData.observe(this,{
            it?.let {
                finish()
            }
        })
    }
}