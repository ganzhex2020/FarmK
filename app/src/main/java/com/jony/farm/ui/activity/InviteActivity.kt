package com.jony.farm.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.format.DateUtils
import androidx.lifecycle.lifecycleScope
import cn.hutool.core.date.DateUtil
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.config.Const.PATH_FILE_PICTURE
import com.jony.farm.util.BitMapUtil
import com.jony.farm.util.FileUtils
import com.jony.farm.util.QRCodeEncoder.syncEncodeQRCode
import com.jony.farm.viewmodel.InviteViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_invite.*
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.getViewModel
import java.io.File

/**
 *Author:ganzhe
 *时间:2021/4/30 17:25
 *描述:This is InviteActivity
 */

@RouterAnno(
    host = Const.MODULE_HOST_APP,
    path = Const.MODULE_PATH_APP_INVITE,
    interceptorNames = [Const.LOGININTERCEPTOR]
)
class InviteActivity :BaseVMActivity<InviteViewModel>(){

    override fun initVM(): InviteViewModel = getViewModel()


    override fun getLayoutResId(): Int = R.layout.activity_invite

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_animalhistory)
        onClick()

    }

    private fun onClick(){
        iv_save.setOnClickListener {
            saveQrcode()
        }

        iv_copy.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.setPrimaryClip(ClipData.newPlainText(null, "xxxx"))
            toast("copy success")
        }
    }



    private fun saveQrcode() = runWithPermissions(Permission.WRITE_EXTERNAL_STORAGE){
        lifecycleScope.launchWhenResumed {
            launch(Dispatchers.Main){
                withContext(Dispatchers.IO){
                    val bitmap = BitMapUtil.Image2Bitmap(iv_qrcode)
               //     val path = getExternalFilesDir("")?.absolutePath + File.separator + System.currentTimeMillis() + ".png"
                    val fileName =  DateUtil.date().toString()

                    BitMapUtil.saveBitmap(this@InviteActivity,fileName, bitmap)
                }
                toast("save success")
            }

        }
    }

    override fun initData() {

    }

    override fun startObserve() {
        lifecycleScope.launchWhenResumed {
            launch(Dispatchers.Main) {
                val bitmap = withContext(Dispatchers.IO){
                    //    syncEncodeQRCode("${sysParam.frontDomain}&code=$inviteCode",150)
                    syncEncodeQRCode("my link content", 150)
                }
                iv_qrcode.setImageBitmap(bitmap)
            }
        }
    }


}