package com.jony.farm.socket

import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.jony.farm.util.DateUtil
import com.jony.farm.util.bus.RxBus
import com.jony.farm.util.bus.sendEvent
import com.tencent.mmkv.MMKV
import okhttp3.*
import okio.ByteString
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object WebSocketUtil {

    var mWebSocket: WebSocket? = null


    @JvmStatic
    fun longConnect(seeeionId: String) {

        val websockUrl = "ws://160.19.50.103:4502/wsapp?user=$seeeionId"
        val okHttpClient = OkHttpClient().newBuilder()
            .pingInterval(90, TimeUnit.SECONDS)
            //    .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
            //  .sslSocketFactory(CertUtil.getSSlSocketFactory(), CertUtil.getX509())
            //  .hostnameVerifier(CertUtil.getVertifer())
            .retryOnConnectionFailure(true)
            .build()
        val request = Request.Builder()
            .url(websockUrl)
            .build()
        mWebSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                LogUtils.error("onOpen ${response.code}")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                LogUtils.error("onMessage1 $text")
                sendEvent(text)
              //  RxBus.getInstance().post(text)
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

    fun sendMsg(targetCategory: Int, targetValue: String):Boolean {
        val kv = MMKV.defaultMMKV()
        var username = kv.decodeString(Constant.KEY_USER_NAME, "")
        val result = JSONObject()
        val tar = JSONObject()

        try {
            tar.put("targetCategory", targetCategory)
            tar.put("targetValue", targetValue)
            result.put("model", 1)
            result.put("msgType", 1)

            if (username.length > 3) {
                username = "***" + username.substring(username.length - 2)
            }
            result.put("msg", username)
            result.put("fromSource", "")
            result.put("target", tar)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        if (mWebSocket !=null){
            val isSend =  mWebSocket!!.send(result.toString())
            val sessionId = kv.decodeString(Constant.KEY_SESSION_ID,"")
            if (!isSend&& sessionId.isNotEmpty()){
                longConnect(sessionId)
            }
            return isSend
        }
        return false

    }

    fun senChatMsg(fromSource:Int,msg:String):Boolean{
        val kv = MMKV.defaultMMKV()
        val userId = kv.decodeInt(Constant.KEY_USER_ID)
        val userName = kv.decodeString(Constant.KEY_USER_NAME)
        val headImg = kv.decodeString(Constant.KEY_USER_AVATAR)
        val result = JSONObject()
        val tar = JSONObject()
        val jsonObject = JSONObject()
        jsonObject.put("name",userName)
        jsonObject.put("content",msg)
        jsonObject.put("headImg",headImg)
        jsonObject.put("betTime",DateUtil.currentDate())



        tar.put("targetCategory", 4)
        tar.put("targetValue", 24)
        result.put("model", 2)
        result.put("msgType", 8)
        result.put("msg", jsonObject.toString())
        result.put("fromSource", fromSource)
        result.put("target", tar)
        result.put("extendData", userId)

        if (mWebSocket !=null){
            val isSend =  mWebSocket!!.send(result.toString())
            val sessionId = kv.decodeString(Constant.KEY_SESSION_ID,"")
            if (!isSend&& sessionId.isNotEmpty()){
                longConnect(sessionId)
            }
            return isSend
        }
        return false
    }
}