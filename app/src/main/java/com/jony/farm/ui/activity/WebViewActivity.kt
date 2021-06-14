package com.jony.farm.ui.activity

import android.widget.LinearLayout
import com.combodia.basemodule.base.BaseActivity
import com.combodia.httplib.config.Constant
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.just.agentweb.AgentWeb
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.activity_webview.ll_parent


/**
 *Author:ganzhe
 *时间:2021/5/28 12:33
 *描述:This is WebViewActivity
 */
class WebViewActivity:BaseActivity() {

    private var agentWeb: AgentWeb? = null

    override fun getLayoutResId(): Int = R.layout.activity_webview

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }


        var url = intent.getStringExtra("webUrl")
        val title = intent.getStringExtra("title")

        tv_title.text = title

       // url = "https://www.google.com/"

        val userName = MMKV.defaultMMKV().decodeString(Constant.KEY_USER_NAME, "")
        if (userName.isNotEmpty()){
            url = "$url&username=$userName"
        }

        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(webContainer, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url)

    }

    override fun initData() {

    }

    override fun onPause() {
        agentWeb!!.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb!!.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}