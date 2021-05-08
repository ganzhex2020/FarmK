package com.combodia.httplib.interceptor

import android.util.Log
import com.combodia.httplib.ext.AuthonService
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.nio.charset.Charset

class HttpLogInterceptor : Interceptor {
    companion object {
        private val UTF8 = Charset.forName("UTF-8")
    }



    //  private val kv: MMKV = MMKV.defaultMMKV()
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestBody = request.body
        val responseBody = response.body
        val responseHeader = response.headers

        val responseBodyString = responseBody!!.string()
        var requestMessage: String
        requestMessage = request.method + ' '.toString() + request.url

        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            requestMessage += "?\n" + buffer.readString(UTF8)
        }
        /**
         * 存储返回的token
         */
/*        val token = responseHeader["token"]
        if (!TextUtils.isEmpty(token)){
            kv.encode(KEY_TOKEN,token)
        }*/
        //存储cookie 方案二
        /*if (response.headers("Set-Cookie").isNotEmpty()) {
            val cookies = HashSet<String>()
            for (header in response.headers("Set-Cookie")) {
                cookies.add(header)
            }
            kv.encode(Constant.KEY_COOKIE, cookies)
        }*/

        Log.d("RequestLog >> ", requestMessage)
        Log.d(
            "ResponseLog << ",
            request.method + ' '.toString() + request.url + ' '.toString() + responseHeader + ' '.toString() + responseBodyString
        )
        // val entity = GsonHelper.convertEntity(responseBodyString)
        /**
         * 拦截未登录情况
         */
        if (response.code == 403 || response.code == 405) {
          //  RxBus.getIntanceBus().post(CusExceptionEvent(response.code,"未登录"))
         //   EventBus.getDefault().post(BusEvent(response.code,"未登录"))
            AuthonService.getInstance().onListener.setCode(response.code)
        }

        return response.newBuilder()
            .body(responseBodyString.toByteArray().toResponseBody(responseBody.contentType()))
            .build()

    }
}