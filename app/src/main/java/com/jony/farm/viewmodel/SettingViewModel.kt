package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.config.Constant
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.combodia.httplib.factory.RetrofitClient
import com.jony.farm.model.api.ServiceApi
import com.jony.farm.model.entity.AppVersion
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingViewModel(private val remoteRepo:RemoteDataSource):BaseViewModel() {

    val versionLiveData = MutableLiveData<AppVersion>()
    val signOutLiveData = MutableLiveData<Boolean>()

    fun getVersion(){
        val serviceApi = RetrofitClient.createApi(ServiceApi::class.java, "http://47.243.119.37:8700/")

        launchUI({
            val result = withContext(Dispatchers.IO){
                serviceApi.getVersion()
            }
            result.checkSuccess {
                versionLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

    fun signOut(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.signOut()
            }
            result.checkSuccess {
                /**
                 * 退出登录 修改缓存登录标志
                 */
                kv.encode(Constant.KEY_LOGIN_STATE,false)

                signOutLiveData.value = true
            }
        },isShowDiaLoading = true)
    }

}