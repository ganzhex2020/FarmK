package com.combodia.httplib.factory


import com.combodia.httplib.ext.CertUtil
import com.combodia.httplib.interceptor.HeaderInterceptor
import com.combodia.httplib.interceptor.HttpLogInterceptor
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private val mOkClient: OkHttpClient

    init {
//        Subject	wanandroid.com
//                Fingerprint SHA256: f25a78babca3ce0c9b3636e598c4384c1329ff2cf5b219f71721ec4ef81cec3d
//                Pin SHA256: a2bAjA/ldOdVOULMNoOhvk8HQr5iJB7N0d8fHYw2dR8=

        /*     val httpCacheDirectory =
                 File(clarityPotion.externalCacheDir, "NetworkCache")
             val cacheSize = 50 * 1024 * 1024 // 50 MB
             val cache = Cache(httpCacheDirectory, cacheSize.toLong())*/

        mOkClient = OkHttpClient.Builder()

          /*  .connectionSpecs(
                listOf(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS,
                    ConnectionSpec.CLEARTEXT
                )
            )*/

            /*   .certificatePinner(
                   CertificatePinner.Builder()
                       .add("wananzhuo.com", "sha256/ldOdVOULMNoOhvk8HQr5iJB7N0d8fHYw2dR8=")
                       .build())*/
            .sslSocketFactory(CertUtil.getSSlSocketFactory(), CertUtil.getX509())
            .hostnameVerifier(CertUtil.getVertifer())
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(HttpLogInterceptor())
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)//设置连接超时时间
            //     .retryOnConnectionFailure(false)   //连接失败不重试
            //      .cache(cache)
            //     .cookieJar(CookieManger(clarityPotion))
            .build()


    }

    private fun <T> createApi(tClass: Class<T>, baseUrl: String): T {

        return Retrofit.Builder()
            .client(mOkClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(tClass)
    }

    companion object {
        private const val CONNECT_TIMEOUT = 10
        private const val READ_TIMEOUT = 20
        private const val WRITE_TIMEOUT = 20

        private val INSTANT: RetrofitClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitClient() }

        fun get(): RetrofitClient {
            return INSTANT
        }

        fun <T> createApi(tClass: Class<T>, baseUrl: String): T {
            return lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                get()
                    .createApi(tClass, baseUrl)
            }.value
        }

    }

}