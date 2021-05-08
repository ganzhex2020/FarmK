package com.jony.farm.util

import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.onekeyshare.OnekeyShare
import com.combodia.basemodule.utils.LogUtils
import com.mob.MobSDK


object ShareUtil {

    private val shareCallback: PlatformActionListener = object : PlatformActionListener {
        override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String?, Any?>?) {
            LogUtils.error("${platform?.name} 分享成功 $hashMap")
        }

        override fun onError(platform: Platform, i: Int, throwable: Throwable) {
            LogUtils.error("${platform?.name} 分享失败 $i ${throwable.message}")
        }

        override fun onCancel(platform: Platform, i: Int) {
            LogUtils.error("${platform?.name} 分享取消 $i ")
        }
    }


    @JvmStatic
    fun shareText() {
        val oks = OnekeyShare()
        // title标题，微信、QQ和QQ空间等平台使用
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("TestShare")
        // titleUrl QQ和QQ空间跳转链接
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn")
        // text是分享文本，所有平台都需要这个字段
        // text是分享文本，所有平台都需要这个字段
        oks.text = "我是分享文本"
        // setImageUrl是网络图片的url
        // setImageUrl是网络图片的url
        oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png")
        // url在微信、Facebook等平台中使用
        // url在微信、Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn")
        // 启动分享GUI
        // 启动分享GUI
        oks.callback = shareCallback
        oks.show(MobSDK.getContext())
    }
}