package com.jony.farm.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.socket.WsManager
import com.jony.farm.socket.WsStatusListener
import com.jony.farm.viewmodel.CommunityViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.layout_common_header.*
import okhttp3.*
import okio.ByteString
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.properties.Delegates

/**
 *Author:ganzhe
 *时间:2021/5/18 16:59
 *描述:This is CommunityActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_COMMUNITYCHAT,interceptorNames = [Const.LOGININTERCEPTOR])
class CommunityActivity:BaseVMActivity<CommunityViewModel>() {

    companion object {
        //var wsManager: WsManager? = null
        var mWebSocket:WebSocket? = null
    }


    override fun initVM(): CommunityViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_community

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_communitychat)

        btn_open.setOnClickListener {
            longConnect()
        }
        btn_send.setOnClickListener {
            if (mWebSocket !=null){
                val isOk =  mWebSocket!!.send("我的android客户端 哈哈")
                LogUtils.error("是否发送成功：$isOk")
            }
        }
        btn_close.setOnClickListener {
            mWebSocket?.close(1000,"客户端主动关闭连接")
        }

    }

    override fun initData() {

    }

    override fun startObserve() {

    }

    private fun longConnect(){
        val websockUrl = "ws://10.6.135.6:8070/websocket"
        val okHttpClient = OkHttpClient().newBuilder()
                //  .pingInterval(40, TimeUnit.SECONDS)
                //    .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                //  .sslSocketFactory(CertUtil.getSSlSocketFactory(), CertUtil.getX509())
                //  .hostnameVerifier(CertUtil.getVertifer())
                .retryOnConnectionFailure(true)
                .build()
        val request = Request.Builder()
                .url(websockUrl)
                .build()
        mWebSocket = okHttpClient.newWebSocket(request, object:WebSocketListener(){
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                LogUtils.error("onOpen ${response.code}")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                LogUtils.error("onMessage1 $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                LogUtils.error("onMessage2 $bytes")
            }


            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                LogUtils.error("onClosing $code $reason")
            }
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                LogUtils.error("onClosed $code $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                LogUtils.error("onFailure $t $response")
            }

        })

    }


    /*private fun longConnect(){
        val okHttpClient = OkHttpClient().newBuilder() //  .pingInterval(40, TimeUnit.SECONDS)
                //    .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                //  .sslSocketFactory(CertUtil.getSSlSocketFactory(), CertUtil.getX509())
                //  .hostnameVerifier(CertUtil.getVertifer())
                .retryOnConnectionFailure(true)
                .build()
        wsManager = WsManager.Builder(this)
                .client(okHttpClient)
                .needReconnect(false)
                .wsUrl("ws://10.6.135.6:8070/websocket") //    .wsUrl(SOCKET_URL)
                .build()
        wsManager?.setWsStatusListener(wsStatusListener)
        wsManager?.startConnect()
    }

    private fun close(){
        wsManager?.stopConnect()
    }

    private val wsStatusListener: WsStatusListener = object : WsStatusListener() {

        override fun onOpen(response: Response?) {
            super.onOpen(response)
            LogUtils.error("WebSocket------onOpen")
            tv.text = "连接打开:${response?.code}"
        }

        @SuppressLint("SetTextI18n")
        override fun onMessage(text: String) {
            super.onMessage(text)
            LogUtils.error("WebSocket -- onMessage:$text")
            tv.text = "接收的消息：$text"
        }

        override fun onMessage(bytes: ByteString?) {
            super.onMessage(bytes)
        }

        override fun onReconnect() {
            super.onReconnect()
            LogUtils.error("WebSocket -- onReconnect:")
        }

        override fun onClosing(code: Int, reason: String) {
            super.onClosing(code, reason)
            LogUtils.error("WebSocket -- onClosing  code:" + code + "reson:" + reason)
            tv.text = "连接关闭:$code $reason"
        }

        override fun onClosed(code: Int, reason: String) {
            super.onClosed(code, reason)
            LogUtils.error("WebSocket -- onClosed  code:" + code + "reson:" + reason)
            tv.text = "连接关闭:$code $reason"
        }

        override fun onFailure(t: Throwable?, response: Response?) {
            super.onFailure(t, response)
            LogUtils.error("WebSocket -- onFailure:$t $response")
            tv.text = "失败 已关闭连接"
        }
    }*/
}