package com.jony.farm.ui.activity

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.config.Constant
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.viewmodel.FundPwdViewModel
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_fundpwd.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel


@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_FUNDPWD, interceptorNames = [Const.LOGININTERCEPTOR])
class FundPwdActivity:BaseVMActivity<FundPwdViewModel>() {

    var isSet = -1

    override fun initVM(): FundPwdViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_fundpwd

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_fundpwd)

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
            et2.setSelection(et2.text.toString().length)
        }
        cb3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                et3.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                et3.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            et3.setSelection(et3.text.toString().length)
        }

        iv_submit.setOnClickListener {
            if (isSet == -1){
                toast(getString(R.string.checknetwork))
                return@setOnClickListener
            }
            if (isSet == 1){//设置
                setPwd()
            }else{//修改
                changePwd()
            }
        }
    }

    private fun setPwd(){
        val oldPwd = et1.text.toString().trim()
        val newPwd = et2.text.toString().trim()
        val rePwd = et3.text.toString().trim()
        if (oldPwd.isEmpty()){
            toast(getString(R.string.plenterloginpwd))
            return
        }
        if (newPwd.isEmpty()){
            toast(getString(R.string.plenternewpwd))
            return
        }
        if (rePwd.isEmpty()){
            toast(getString(R.string.plconfirmpwd))
            return
        }
        if (newPwd != rePwd){
            toast(getString(R.string.pwdenternotequal))
            return
        }
        val map = HashMap<String,Any>()
        map["password"] = oldPwd
        map["pwd"] = newPwd
        map["safeLevel"] = 2
        mViewModel.setPwd(map)
    }

    private fun changePwd(){
        val oldPwd = et1.text.toString().trim()
        val newPwd = et2.text.toString().trim()
        val rePwd = et3.text.toString().trim()
        if (oldPwd.isEmpty()){
            toast(getString(R.string.plenteroldpwd))
            return
        }
        if (newPwd.isEmpty()){
            toast(getString(R.string.plenternewpwd))
            return
        }
        if (rePwd.isEmpty()){
            toast(getString(R.string.plconfirmpwd))
            return
        }
        if (newPwd != rePwd){
            toast(getString(R.string.pwdenternotequal))
            return
        }
        val map = HashMap<String,Any>()
        map["oldPwd"] = oldPwd
        map["newPwd"] = newPwd
        map["safeLevel"] = 2
        mViewModel.changePwd(map)
    }

    override fun initData() {
        mViewModel.getSafeInfo()

    }

    override fun startObserve() {

        mViewModel.run {
           /* memberLiveData.observe(this@FundPwdActivity,{list ->
                list.filter { it.userID == MMKV.defaultMMKV().decodeInt(Constant.KEY_USER_ID) }
                    .map {
                        if (it.trueName.isEmpty()){
                            haveTrueName = false
                            et1.hint = getString(R.string.plenterloginpwd)
                        }
                    }
            })*/
            safeInfoLiveData.observe(this@FundPwdActivity,{
                isSet = if (it.first().isWithPwd == 1){
                    et1.hint = getString(R.string.plenterloginpwd)
                    1//设置
                }else{
                    0//修改
                }
            })
            successLiveData.observe(this@FundPwdActivity,{
                if (it){
                    finish()
                }
            })
        }
    }
}