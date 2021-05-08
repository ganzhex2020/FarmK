package com.combodia.httplib.interceptor

import com.combodia.httplib.config.Constant.KEY_COOKIE
import com.combodia.httplib.config.Constant.KEY_TOKEN
import com.tencent.mmkv.MMKV
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    private val kv: MMKV = MMKV.defaultMMKV()
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Authorization","Token "+kv.decodeString(KEY_TOKEN,""))
        //    .addHeader("userid", kv.decodeInt(KEY_USER_ID).toString())
        //    .addHeader("imei",kv.decodeString(KEY_IMEI,""))
        //    .addHeader("imeiType","android")
        // .build()
        //todo 添加头部header 一般情况下 if isLogin 从kv中获取token .addHeader("token",kv.decodeString(KEY_TOKEN,""))
        //从kv读取cookie cookie存储的方案二
       /* val cookies = kv.decodeStringSet(KEY_COOKIE, emptySet())
        for (cookie in cookies) {
            builder.addHeader("Cookie", cookie)
        }*/

        val build = builder.build()
        return chain.proceed(build)

    }

}