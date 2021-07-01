package com.jony.farm.ui.activity

import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.GlideUtils
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const.LOGININTERCEPTOR
import com.jony.farm.config.Const.MODULE_HOST_APP
import com.jony.farm.config.Const.MODULE_PATH_APP_USERINFO
import com.jony.farm.util.FileUtils
import com.jony.farm.util.GlideEngine
import com.jony.farm.view.dialog.SetConfigDialog
import com.jony.farm.viewmodel.UserInfoViewModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_userinfo.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/16 17:10
 *描述:This is UserInfoActivity
 */

@RouterAnno(
    host = MODULE_HOST_APP,
    path = MODULE_PATH_APP_USERINFO,
    interceptorNames = [LOGININTERCEPTOR]
)
class UserInfoActivity:BaseVMActivity<UserInfoViewModel>(){

    var clickable = false

    override fun initVM(): UserInfoViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_userinfo

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_personaldata)


        iv_submit.setOnClickListener {
          //  updateUserInfo()
            clickable = !clickable
        }
        rl_head.setOnClickListener {
            uploadHead()
        }
        ll_nickname.setOnClickListener {
            if (clickable){
                val setConfig = SetConfigDialog(this,getString(R.string.userinfo_setnickname),tv_nickname.text.toString())
                setConfig.setOnClickListener {
                    tv_nickname.text = it
                    updateUserInfo()
                }
                setConfig.show()
            }

        }
        ll_facebook.setOnClickListener {
            if (clickable){
                val setConfig = SetConfigDialog(this,getString(R.string.userinfo_setfacebook),tv_facebook.text.toString())
                setConfig.setOnClickListener {
                    tv_facebook.text = it
                    updateUserInfo()
                }
                setConfig.show()
            }
        }
    }

    private fun updateUserInfo(){
        val email = tv_email.text.toString().trim()
        val nickName = tv_nickname.text.toString().trim()
        val tel = tv_phone.text.toString().trim()
        val facebook = tv_facebook.text.toString().trim()

        val map = HashMap<String, Any>()
        map["email"] = email
        map["nickName"] = nickName
        map["tel"] = tel
        map["qq"] = facebook

        mViewModel.updateUserInfo(map)
    }

    private fun uploadHead() = runWithPermissions(Permission.WRITE_EXTERNAL_STORAGE){
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage()) //.maxSelectNum(1)
            .selectionMode(PictureConfig.SINGLE)
            .imageEngine(GlideEngine.createGlideEngine())
            .isCompress(true)
            .compressQuality(80)
            .withAspectRatio(1, 1) // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            //  .cutOutQuality(50)
            //   .minimumCompressSize(300)// 小于多少kb的图片不压缩
            .isEnableCrop(true) // 是否裁剪
            .circleDimmedLayer(true) // 是否圆形裁剪
            .showCropFrame(false) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
            .showCropGrid(false) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: List<LocalMedia>) {
                    LogUtils.error(result)
                    // onResult Callback
                    if (result.isNotEmpty()) {
                        LogUtils.error(result[0].cutPath)
                        LogUtils.error(FileUtils.calFileSize(result[0].cutPath))
                        mViewModel.uploadHead(result[0].cutPath)
                    }
                }

                override fun onCancel() {
                    // onCancel Callback
                }
            })
    }

    override fun initData() {
        mViewModel.getUserInfo()
    }
    override fun startObserve() {
        mViewModel.memberLiveData.observe(this, { list ->
            list.filter { it.userID == MMKV.defaultMMKV().decodeInt(Constant.KEY_USER_ID) }
                .map {
                    //  LogUtils.error("xxasda")
                    tv_username.text = it.userName
                    tv_nickname.text = it.nickName
                    tv_phone.text = it.telephone
                    tv_email.text = it.email
                    tv_facebook.text = it.qq
                    if (it.headImg.isNotEmpty()) {
                        GlideUtils.loadCircleImage(iv_head, it.headImg)
                    }
                }
        })
    }
}