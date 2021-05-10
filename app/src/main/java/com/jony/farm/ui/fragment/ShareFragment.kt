package com.jony.farm.ui.fragment

import cn.sharesdk.facebook.Facebook
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.onekeyshare.OnekeyShare
import cn.sharesdk.telegram.Telegram
import cn.sharesdk.twitter.Twitter
import cn.sharesdk.whatsapp.WhatsApp
import com.combodia.basemodule.base.BaseVMFragment
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.model.entity.ShareCountEntity
import com.jony.farm.viewmodel.ShareViewModel
import com.mob.MobSDK
import kotlinx.android.synthetic.main.fragment_share.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/23 20:16
 *描述:This is ShareFragment
 */
class ShareFragment : BaseVMFragment<ShareViewModel>() {

    var shareCountEntity: ShareCountEntity? = null
    //  val platforms = mutableListOf<String>()


    override fun initVM(): ShareViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.fragment_share

    override fun initView() {
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        /* val platformList = ShareSDK.getPlatformList()
         platformList.forEach {
             platforms.add(it.name)
         }*/
        refreshLayout.setOnRefreshListener {
            mViewModel.getShareCount()
            it.finishRefresh(2000)
        }

        onClick()
    }

    private fun onClick() {
        /**
         * 1:fb
        2:tittwer
        3:聊天群
        4:每日签到
        5:幸运转盘
         */
        rl_facebook.setOnClickListener {
            if (shareCountEntity == null) {
                toast("请刷新")
                return@setOnClickListener
            }
            if (shareCountEntity!!.fb == 0) {
                toast("今日facebook分享次数已用完")
                return@setOnClickListener
            }
            shareText(ShareSDK.getPlatform(Facebook.NAME))
        }
        rl_twitter.setOnClickListener {
            if (shareCountEntity == null) {
                toast("请刷新")
                return@setOnClickListener
            }
            if (shareCountEntity!!.tiw == 0) {
                toast("今日twitter分享次数已用完")
                return@setOnClickListener
            }
            shareText(ShareSDK.getPlatform(Twitter.NAME))
        }
        rl_checkin.setOnClickListener {
            if (shareCountEntity == null) {
                toast("请刷新")
                return@setOnClickListener
            }
            if (shareCountEntity!!.sign == 0) {
                toast("今日已签到")
                return@setOnClickListener
            }
            mViewModel.getSharefodder(4)
        }
        rl_whatsapp.setOnClickListener {
            if (shareCountEntity == null) {
                toast("请刷新")
                return@setOnClickListener
            }
            if (shareCountEntity!!.ctApp == 0) {
                toast("今日WhatsApp分享次数已用完")
                return@setOnClickListener
            }
            shareText(ShareSDK.getPlatform(WhatsApp.NAME))
        }
        rl_telegram.setOnClickListener {
            if (shareCountEntity == null) {
                toast("请刷新")
                return@setOnClickListener
            }
            if (shareCountEntity!!.ctApp == 0) {
                toast("今日Telegram分享次数已用完")
                return@setOnClickListener
            }
            shareText(ShareSDK.getPlatform(WhatsApp.NAME))
        }
    }

    override fun initData() {
        mViewModel.getShareCount()
    }


    override fun startObserve() {

        mViewModel.run {
            sharecountLiveData.observe(viewLifecycleOwner, {
                shareCountEntity = it
            })
            sharefodderLiveData.observe(viewLifecycleOwner, { map ->
                val shareType = map["shareType"] as Int
                val shaCount = map["shareCountEntity"] as ShareCountEntity
                shareCountEntity = shaCount
                LogUtils.error("XXX:$shaCount")
                if (shareType != 4) {
                    toast("分享成功")
                } else {
                    toast("签到成功")
                }
            })
        }
    }

    private val shareCallback: PlatformActionListener = object : PlatformActionListener {
        override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String?, Any?>?) {
            LogUtils.error("${platform?.name} 分享成功 $hashMap")
            when (platform?.name) {
                Facebook.NAME -> {
                    mViewModel.getSharefodder(1)
                }
                Twitter.NAME -> {
                    mViewModel.getSharefodder(2)
                }
                WhatsApp.NAME -> {
                    mViewModel.getSharefodder(3)
                }
                Telegram.NAME -> {
                    mViewModel.getSharefodder(3)
                }
            }
        }

        override fun onError(platform: Platform, i: Int, throwable: Throwable) {
            LogUtils.error("${platform?.name} 分享失败 $i ${throwable.message}")
        }

        override fun onCancel(platform: Platform, i: Int) {
            LogUtils.error("${platform?.name} 分享取消 $i ")
        }
    }

    private fun shareText(platform: Platform) {
        /*val oks = OnekeyShare()
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
        oks.show(MobSDK.getContext())*/
        val shareParams = Platform.ShareParams()
        shareParams.imageUrl = "https://hmls.hfbank.com.cn/hfapp-api/9.png"
        //   shareParams.title = "测试话题分享"
        // shareParams.text = "测试话题我是共用的参数，这几个平台都有text参数要求，提取出来啦"
        //shareParams.kakaoWebUrl
        //shareParams.shareType = Platform.SHARE_IMAGE
        shareParams.hashtag = "https://test1881.com I am so happy"
        shareParams.shareType = Platform.SHARE_IMAGE
        platform.platformActionListener = shareCallback
        platform.share(shareParams)
    }

    private fun oneKeyShare() {
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