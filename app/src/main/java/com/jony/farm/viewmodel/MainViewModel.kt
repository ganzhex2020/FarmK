package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.combodia.httplib.factory.RetrofitClient
import com.jony.farm.config.Const
import com.jony.farm.model.api.ServiceApi
import com.jony.farm.model.entity.AppVersion
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2020/10/21 22:32
 *描述:This is MainViewModel
 */
class MainViewModel(private val remoteRepo: RemoteDataSource):BaseViewModel() {

  //  val userLiveData = MutableLiveData<UserInfo>()

    fun test(){
        /*launchUI(
            {
                val reslut1 = async(Dispatchers.IO) {
                    repository.getHomeArticles(0)
                }
                reslut1.await().checkSuccess {
                    LogUtils.error(it)
                }
                reslut1.await().checkError {
                    LogUtils.error(it.errorMsg)
                }
            }
        )*/
        /*viewModelScope.launch(Dispatchers.Main){
            val res1 = async(Dispatchers.IO){
                repository.test(0)
            }
           *//* val res2 = async(Dispatchers.IO){
                repository.test(1)
            }*//*
            res1.cancelAndJoin()
            res1.await().checkSuccess {
                LogUtils.error(it)
            }

            *//*res2.await().checkSuccess {
                LogUtils.error(it)
            }*//*
        }*/
    }
    val versionLiveData = MutableLiveData<AppVersion>()
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
}