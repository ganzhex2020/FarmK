package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant.KEY_LOGIN_STATE
import com.combodia.httplib.config.Constant.KEY_TOKEN
import com.combodia.httplib.config.Constant.KEY_USER_ID
import com.combodia.httplib.config.Constant.KEY_USER_NAME
import com.combodia.httplib.config.Constant.KEY_USER_PWD
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.app.MyApp
import com.jony.farm.config.Const
import com.jony.farm.db.AppDatabase
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException

/**
 *Author:ganzhe
 *时间:2021/4/15 14:15
 *描述:This is LoginViewModel
 */
class LoginViewModel(private val remoteRepo: RemoteDataSource,private val localRepo:LocalDataSource) : BaseViewModel() {

    val loginStateLiveData = MutableLiveData<Boolean>()

    fun login(userName:String,passwd:String) {
//        val userName = "ganzhe777"
//        val passwd = "123456"
        val map = HashMap<String, Any>()
        map["userName"] = userName
        map["password"] = passwd
        map["appID"] = Const.APP_ID
        map["prefix"] = Const.COMPANY
        map["device"] = 0
        val requestBody = MapUtils.map2JsonRequestBody(map)
        launchUI({
            val result = withContext(Dispatchers.IO) {
                remoteRepo.login(requestBody)
            }
            result.checkSuccess {
                //LogUtils.error(it)
                kv.encode(KEY_USER_ID,it.userID)
                kv.encode(KEY_USER_NAME,userName)
                kv.encode(KEY_USER_PWD,passwd)
                kv.encode(KEY_LOGIN_STATE,true)
                kv.encode(KEY_TOKEN,it.ticket)
                withContext(Dispatchers.IO){
                //    localrepo.deleteAll()
                    localRepo.insertUserInfo(it)
                }
                val member = withContext(Dispatchers.IO){
                    remoteRepo.getMembers()
                }
                member.checkSuccess { memverEntiry ->
                    localRepo.insertMember(memverEntiry.copy(password = passwd))
                }
                loginStateLiveData.value = true
            }

            result.checkError {
                LogUtils.error(it.errorMsg)
            }
        }, isShowDiaLoading = true)
    }

    fun test1(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                remoteRepo.getAnimalLeftCount(2)
            }
            result.checkSuccess {
                LogUtils.error(it)
            }
            result.checkError {
                LogUtils.error(it.errorMsg)
            }
        }
    }
}