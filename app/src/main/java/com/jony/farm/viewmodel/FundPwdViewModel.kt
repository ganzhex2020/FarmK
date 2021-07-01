package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.SafeInfoEntity
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FundPwdViewModel(private val remoteRepo:RemoteDataSource,private val localRepo:LocalDataSource):BaseViewModel() {

   // val memberLiveData = localRepo.getMemberLiveData()
    val safeInfoLiveData = MutableLiveData<List<SafeInfoEntity>>()
    val successLiveData = MutableLiveData<Boolean>()

    fun getSafeInfo(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getSafeCheck()
            }
            result.checkSuccess {
                safeInfoLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

    fun changePwd(map:Map<String,Any>){
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.changeFundPwd(body)
            }
            result.checkSuccess {
                toast(result.message)
                successLiveData.value = true
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }

    fun setPwd(map:Map<String,Any>){
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.setFundPwd(body)
            }
            result.checkSuccess {
                toast(result.message)
                successLiveData.value = true
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }


}