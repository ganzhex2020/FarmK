package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/15 14:11
 *描述:This is LoginActivity
 */
class LoginActivity:BaseVMActivity<LoginViewModel>() {



  //  val exceptionManager by inject<ExceptionManager>()

    override fun initVM(): LoginViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }



        iv_login_submit.setOnClickListener {
            login()
        }

        /**
         * 初始化设置密码
         */


    }
    private fun login(){
        val userName = et_username.text.toString().trim()
        val passwd = et_passwd.text.toString().trim()
        if (userName.isBlank()){
            toast("Please enter username")
            return
        }
        if (passwd.isBlank()){
            toast("Please enter password")
            return
        }
        mViewModel.login(userName,passwd)
    }

    override fun initData() {

    }

    override fun startObserve() {
        mViewModel.run {
            loginStateLiveData.observe(this@LoginActivity,{
                if (it){
                    LogUtils.error("登录成功，退出activity")
                    finish()
                }
            })
        }
    }
}