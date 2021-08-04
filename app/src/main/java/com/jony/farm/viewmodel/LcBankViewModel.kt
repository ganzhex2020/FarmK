package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.RateEntity
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LcBankViewModel(val remoteRepo: RemoteDataSource,val localRepo:LocalDataSource) : BaseViewModel() {

    val yueLiveData = localRepo.getYueLiveData()
    val rateLiveData = MutableLiveData<RateEntity>()
    val lcDhLiveData = MutableLiveData<Boolean>()
    val lcZzLiveData = MutableLiveData<Boolean>()

    fun getData(){
        viewModelScope.launch {
            val yue = withContext(Dispatchers.IO){
                remoteRepo.getYue()
            }
            val rate = withContext(Dispatchers.IO){
                remoteRepo.getRate()
            }
            yue.checkSuccess {
                localRepo.deleteYue()
                localRepo.insertYue(it)
            }
            rate.checkSuccess {
                rateLiveData.value = it
            }
        }
    }

    fun lcDh(map: Map<String,String>){
        val body = MapUtils.map2JsonRequestBody(map)
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.lcDh(body)
            }
            result.checkSuccess {
                val yue = withContext(Dispatchers.IO){
                    remoteRepo.getYue()
                }
                yue.checkSuccess {
                    localRepo.deleteYue()
                    localRepo.insertYue(it)
                }
                toast("兑换成功")
                lcDhLiveData.value = true
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }

    fun lcZz(map:Map<String,String>){
        val body = MapUtils.map2JsonRequestBody(map)
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.lcZz(body)
            }
            result.checkSuccess {
                val yue = withContext(Dispatchers.IO){
                    remoteRepo.getYue()
                }
                yue.checkSuccess {
                    localRepo.deleteYue()
                    localRepo.insertYue(it)
                }
                toast("转账成功")
                lcZzLiveData.value = true
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }
}