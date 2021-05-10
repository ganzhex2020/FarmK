package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/5/1 20:33
 *描述:This is RegisterViewModel
 */
class RegisterViewModel (val remoteRepo: RemoteDataSource) :BaseViewModel(){

    val regisLiveData = MutableLiveData<Boolean>()

    fun register(map: Map<String,Any>){

        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.register(body)
            }
            result.checkSuccess {
                regisLiveData.value = true
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)

    }

}