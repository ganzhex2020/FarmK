package com.jony.farm.view.dialog

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.jony.farm.R
import com.jony.farm.model.entity.AppVersion
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.gone
import com.jony.farm.util.installApk
import com.jony.farm.util.visible
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.dialog_upgradeversion.*
import zlc.season.rxdownload4.file
import zlc.season.rxdownload4.manager.*


/**
 *Author:ganzhe
 *时间:2020/12/25 11:09
 *描述:This is VersionUpdateDialog
 */
class UpgradeVersionDialog(context: Context,activity: Activity, appVersion: AppVersion) :
    Dialog(
        context,
        R.style.dialog_center_full
    ) {


    init {

        setCanceledOnTouchOutside(!appVersion.force)
        setCanceledOnTouchOutside(!appVersion.force)
        setCancelable(!appVersion.force)
        val view = View.inflate(context, R.layout.dialog_upgradeversion, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 40f),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        tvContent.text = appVersion.des
        val rxPermissions = RxPermissions((activity as FragmentActivity))

        tvUpgrad.setOnClickListener {
            rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { aBoolean: Boolean ->
                    if (aBoolean) {
                        downApk(appVersion)
                    } else {
                        toast("没有权限")
                    }
                }

        }

    }

    private fun downApk(appVersion: AppVersion)  {
        tvUpgrad.gone()
        val url = appVersion.downUrl
        val taskManager = url.manager()
        val tag = taskManager.subscribe { status ->
            // Receive download status
            when (status) {
                is Normal -> {
                }
                is Started -> {
                }
                is Downloading -> {
                    LogUtils.error("${status.progress.downloadSizeStr()}/${status.progress.totalSizeStr()} _ ${status.progress.percent()}")
                    progressbar_update.max = status.progress.totalSize.toInt()
                    progressbar_update.progress = status.progress.downloadSize.toInt()
                    tv_update_persent.text = "${status.progress.percent()}%"
                    tv_update_status.text = context.getString(R.string.downloading)
                }
                is Paused -> {
                }
                is Completed -> {
                    LogUtils.error(context.getString(R.string.download_complete))
                    tvUpgrad.visible()
                    tv_update_status.text = context.getString(R.string.download_complete)
                    val file = url.file()
                    if (file.exists()) {
                        context.installApk(file)
                    }
                }
                is Failed -> {
                    LogUtils.error(context.getString(R.string.download_error))
                    tv_update_status.visible()
                    tvUpgrad.text = context.getString(R.string.toUpdate)
                }
                is Deleted -> {
                }
            }
        }
        taskManager.start()

    }


}