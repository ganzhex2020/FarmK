package com.jony.farm.config

import android.content.Context
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.RxBus
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlin.properties.Delegates

/**
 *Author:ganzhe
 *时间:2021/4/15 19:15
 *描述:This is ComExt
 */
var rxBus: RxBus by Delegates.notNull()
//注册
fun <T> registerRxBus(context: Context, eventType: Class<T>, action: Consumer<T>) {
    val disposable: Disposable =
        rxBus.doSubscribe(eventType, action,
            {
                LogUtils.error(it.message)
            })
    rxBus.addSubscription(context, disposable)
}