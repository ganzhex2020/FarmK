package com.jony.farm.interceptor


import com.combodia.httplib.config.Constant
import com.jony.farm.config.Const.LOGININTERCEPTOR
import com.jony.farm.util.RouteUtil
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.anno.InterceptorAnno
import com.xiaojinzi.component.impl.RouterInterceptor
import zlc.season.claritypotion.ClarityPotion


@InterceptorAnno(LOGININTERCEPTOR)
class LoginInterceptor: RouterInterceptor {
    private val kv: MMKV = MMKV.defaultMMKV()

    override fun intercept(chain: RouterInterceptor.Chain) {

        /**
         * 路由登陆参数验证
         */
        val loginState = kv.decodeBool(Constant.KEY_LOGIN_STATE, false)
        if (loginState){
            chain.proceed(chain.request())
        }else{
            ClarityPotion.currentActivity()?.let { RouteUtil.start2Login(it) }
        }
    }
}