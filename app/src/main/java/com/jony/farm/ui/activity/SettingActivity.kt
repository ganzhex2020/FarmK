package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.util.CommonUtil
import com.jony.farm.util.gone
import com.jony.farm.util.visible
import com.jony.farm.view.dialog.LanguageDialog
import com.jony.farm.view.dialog.UpgradeVersionDialog
import com.jony.farm.viewmodel.SettingViewModel
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_SETTING)
class SettingActivity:BaseVMActivity<SettingViewModel>() {

    val kv = MMKV.defaultMMKV()

    override fun initVM(): SettingViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_setting

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_setting)
        tv_version.text = CommonUtil.getLocalVersionName(this)

        val isLogin = kv.decodeBool(Constant.KEY_LOGIN_STATE,false)
        if (isLogin){
            rl_tcxt.visible()
        }else{
            rl_tcxt.gone()
        }
    }

    override fun initData() {

        rl_check_version.setOnClickListener {
            mViewModel.getVersion()
        }
        rl_tcxt.setOnClickListener {
            mViewModel.signOut()
        }
        rl_switch_language.setOnClickListener {
            val settingDialog = LanguageDialog(this)
            settingDialog.show()
        }
    }

    override fun startObserve() {
        mViewModel.versionLiveData.observe(this,{
            //LogUtils.error(it)
            val remoteVersionName = it.versionName
            val localVersionName = CommonUtil.getLocalVersionName(this)
            if (remoteVersionName > localVersionName){
                UpgradeVersionDialog(this,this,it).show()
            }else{
                toast(getString(R.string.already_new_version))
            }
        })
        mViewModel.signOutLiveData.observe(this,{
            if (it){
                rl_tcxt.gone()
            }
        })
    }
}