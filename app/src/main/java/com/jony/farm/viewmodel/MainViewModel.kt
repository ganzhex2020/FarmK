package com.jony.farm.viewmodel

import com.combodia.basemodule.base.BaseViewModel
import com.jony.farm.model.repository.RemoteDataSource

/**
 *Author:ganzhe
 *时间:2020/10/21 22:32
 *描述:This is MainViewModel
 */
class MainViewModel(private val repository: RemoteDataSource):BaseViewModel() {

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
}