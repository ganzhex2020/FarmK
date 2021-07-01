package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.ZnxEntity
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SmsViewModel(private val remoteRepo:RemoteDataSource):BaseViewModel() {


    val smsLiveData = MutableLiveData<List<ZnxEntity>>()

    fun getZnMsg(pageIndex:Int,pageSize:Int){

        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getZnMsg(pageIndex,pageSize)
            }
            result.checkSuccess {
                smsLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = pageIndex==1)

    }

    fun setRead(id:Int){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.setRead(id)
            }
            result.checkSuccess {
                LogUtils.error("set read success")
            }
        })
    }
}