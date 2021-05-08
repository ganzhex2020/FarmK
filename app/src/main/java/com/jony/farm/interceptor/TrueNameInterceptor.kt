package com.jony.farm.interceptor

import com.combodia.basemodule.base.BaseActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.combodia.httplib.ext.handleException
import com.jony.farm.app.MyApp
import com.jony.farm.config.Const.TRUENAMEINTERCEPTOR
import com.jony.farm.db.AppDatabase
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import com.jony.farm.view.dialog.CreditDialog
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.anno.InterceptorAnno
import com.xiaojinzi.component.impl.RouterInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import zlc.season.claritypotion.ClarityPotion

/**
 *Author:ganzhe
 *时间:2021/4/27 18:20
 *描述:This is TrueNameInterceptor
 */
@KoinApiExtension
@InterceptorAnno(TRUENAMEINTERCEPTOR)
class TrueNameInterceptor : RouterInterceptor, KoinComponent {

    private val kv: MMKV = MMKV.defaultMMKV()
    private val appDatabase = AppDatabase.getInstance(MyApp.CONTEXT)

    private val remoteDataSource by inject<RemoteDataSource>()
    private val localDataSource by inject<LocalDataSource>()

    override fun intercept(chain: RouterInterceptor.Chain) {

        GlobalScope.launch(Dispatchers.Main) {
            val userId = kv.decodeInt(Constant.KEY_USER_ID)
            val members = withContext(Dispatchers.IO) {
                appDatabase.memberDao().getMembers()
            }
            var haveTrueName = false
            members.filter { it.userID == userId }
                .map {
                    if (it.trueName.isNotEmpty()) {
                        haveTrueName = true
                    }
                }

            if (haveTrueName) {
                chain.proceed(chain.request())
            } else {
                val context = ClarityPotion.currentActivity()

                if (context != null) {
                    val dialog = CreditDialog(context)
                    dialog.setOnClickListener { name, phone, passwd ->
                        LogUtils.error("name:$name phone:$phone passwd:$passwd")
                        val map = HashMap<String, Any>()
                        map["trueName"] = name
                        map["email"] = ""
                        map["phone"] = phone
                        map["password"] = passwd
                        map["safeLevel"] = 2
                        setCredit(map, chain, dialog)

                    }
                    dialog.show()
                }
            }

        }

    }


    fun setCredit(map: Map<String, Any>, chain: RouterInterceptor.Chain, dialog: CreditDialog) {
        val body = MapUtils.map2JsonRequestBody(map)
        val context = ClarityPotion.currentActivity()
        val activity = context as BaseActivity

        GlobalScope.launch(Dispatchers.Main) {
            activity.showDialogLoading()
            val result = withContext(Dispatchers.IO) {
                remoteDataSource.setCreditInfo(body)
            }
            result.checkSuccess {
                toast("真实身份认证成功")
                LogUtils.error("success")
                runCatching {
                    val member = withContext(Dispatchers.IO){
                        remoteDataSource.getMembers()
                    }
                   /* val localmembers = withContext(Dispatchers.IO){
                        localDataSource.getMembers()
                    }
                    var passwd = ""
                    localmembers.filter { it.userID == kv.decodeInt(Constant.KEY_USER_ID) }
                        .forEach {  passwd = it.password}*/
                    member.checkSuccess { memverEntiry ->
                        localDataSource.insertMember(memverEntiry.copy(password = kv.decodeString(Constant.KEY_USER_PWD)))
                    }
                    dialog.dismiss()
                    chain.proceed(chain.request())
                }.onFailure {
                    val exception = handleException(it)
                    toast(exception.errorMsg)
                }
            }
            result.checkError {
                LogUtils.error(it.errorMsg)
            }
            activity.hideDialogLoading()

        }


    }
}