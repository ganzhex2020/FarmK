package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.util.RouteUtil
import com.jony.farm.viewmodel.LoginViewModel
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/15 14:11
 *描述:This is LoginActivity
 */
class LoginActivity:BaseVMActivity<LoginViewModel>() {
    val kv = MMKV.defaultMMKV()


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
        val isRember = kv.decodeBool(Constant.KEY_REMBER_PWD)
        val username = kv.decodeString(Constant.KEY_USER_NAME)
        val passwd = kv.decodeString(Constant.KEY_USER_PWD)
        et_username.setText(username)
        if (isRember){
            et_passwd.setText(passwd)
        }else{
            et_passwd.setText("")
        }
        cb_rember.isChecked = isRember

        onClick()

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
        mViewModel.login(userName,passwd,cb_rember.isChecked)
    }

    private fun onClick(){
        tv_newaccount.setOnClickListener {
            RouteUtil.start2Register(this)
        }
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