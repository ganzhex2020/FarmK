package com.jony.farm.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.Html
import androidx.lifecycle.lifecycleScope
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.util.BitMapUtil
import com.jony.farm.util.DateUtil
import com.jony.farm.util.QRCodeEncoder
import com.jony.farm.viewmodel.TeamPromoteViewModel
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_teampromote.*
import kotlinx.android.synthetic.main.activity_teampromote.iv_copy
import kotlinx.android.synthetic.main.activity_teampromote.iv_qrcode
import kotlinx.android.synthetic.main.activity_teampromote.iv_save
import kotlinx.android.synthetic.main.activity_teampromote.ll_parent
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.getViewModel

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_TEAMPROMOTE, interceptorNames = [Const.LOGININTERCEPTOR])
class TeamPromoteActivity:BaseVMActivity<TeamPromoteViewModel>() {

    private var linkUrl = ""


    override fun initVM(): TeamPromoteViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_teampromote

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_teampromote)
        onClick()
    }

    override fun initData() {
        mViewModel.getLinks()
    }

    private fun onClick(){
        iv_save.setOnClickListener {
            saveQrcode()
        }

        iv_copy.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.setPrimaryClip(ClipData.newPlainText(null, linkUrl))
            toast(getString(R.string.copy_success))
        }
    }

    private fun saveQrcode() = runWithPermissions(Permission.WRITE_EXTERNAL_STORAGE){
        lifecycleScope.launchWhenResumed {
            launch(Dispatchers.Main){
                withContext(Dispatchers.IO){
                    val bitmap = BitMapUtil.Image2Bitmap(iv_qrcode)
                    //     val path = getExternalFilesDir("")?.absolutePath + File.separator + System.currentTimeMillis() + ".png"
                    val fileName =  DateUtil.currentDate()

                    BitMapUtil.saveBitmap(this@TeamPromoteActivity,fileName, bitmap)
                }
                toast(getString(R.string.save_success))
            }

        }
    }

    override fun startObserve() {
        mViewModel.memberLiveData.observe(this,{list ->
            list.filter { it.userID == MMKV.defaultMMKV().decodeInt(Constant.KEY_USER_ID) }
                .map {
                    tv_agentname.text = Html.fromHtml(String.format(getString(R.string.teampromote_name),it.userName))//it.userName
                }
        })
        mViewModel.linkLiveData.observe(this,{ url ->
            LogUtils.error(url)
          //  tv_invitecode.text = url.substring(url.lastIndexOf("=")+1)
            linkUrl = url
            lifecycleScope.launchWhenResumed {
                launch(Dispatchers.Main) {
                    val bitmap = withContext(Dispatchers.IO){
                        //    syncEncodeQRCode("${sysParam.frontDomain}&code=$inviteCode",150)
                        QRCodeEncoder.syncEncodeQRCode(url, 40)
                    }
                    iv_qrcode.setImageBitmap(bitmap)
                }
            }
        })
    }
}