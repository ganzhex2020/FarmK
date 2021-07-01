package com.jony.farm.ui.activity

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.viewmodel.ChangePwdViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_changepwd.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/29 18:30
 *描述:This is ChangePwdActivity
 */
@RouterAnno(
    host = Const.MODULE_HOST_APP,
    path = Const.MODULE_PATH_APP_CHANGEPWD,
    interceptorNames = [Const.LOGININTERCEPTOR]
)
class ChangePwdActivity:BaseVMActivity<ChangePwdViewModel>() {



    override fun initVM(): ChangePwdViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_changepwd

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_updatepwd)

        cb1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                et1.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                et1.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            et1.setSelection(et1.text.toString().length)
        }
        cb2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                et2.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                et2.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            et2.setSelection(et1.text.toString().length)
        }
        cb3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                et3.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                et3.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            et3.setSelection(et1.text.toString().length)
        }
        iv_submit.setOnClickListener {
            updatePwd()
        }
    }

    private fun updatePwd(){
        val oldPwd = et1.text.toString().trim()
        val newPwd = et2.text.toString().trim()
        val confirmPwd = et3.text.toString().trim()
        if (oldPwd.isBlank()){
            toast("请输入旧密码")
            return
        }
        if (newPwd.isBlank()){
            toast("请输入新密码")
            return
        }
        if (confirmPwd.isBlank()){
            toast("请确认新密码")
            return
        }
        if (confirmPwd != newPwd){
            toast("请确认2次输入的新密码是否一样")
            return
        }
        val map = HashMap<String,Any>()
        map["oldPwd"] = oldPwd
        map["newPwd"] = newPwd
        map["safeLevel"] = 2
        mViewModel.updatePwd(map)

    }

    override fun initData() {


    }

    override fun startObserve() {
        mViewModel.updateLiveData.observe(this,{
            if (it){
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}