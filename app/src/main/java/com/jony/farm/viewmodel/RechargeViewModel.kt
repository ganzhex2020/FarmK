package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.OnLinePayEntity
import com.jony.farm.model.entity.PayTypeEntity
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/25 13:10
 *描述:This is RechargeViewModel
 */
class RechargeViewModel(private val remoteRepo: RemoteDataSource):BaseViewModel() {

    val payTypeLiveData = MutableLiveData<List<PayTypeEntity>>()
    val payOnLineLiveData = MutableLiveData<OnLinePayEntity>()

    fun getPayTypeList(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getPayTypeList()
            }
            result.checkSuccess {
                payTypeLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }


    fun pay(map: Map<String,Any>){
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.onlinePay(body)
            }
            result.checkSuccess {
                payOnLineLiveData.value = it
                toast(result.message)
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)

    }

}