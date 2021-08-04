package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.config.Constant
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.ShareContentEntity
import com.jony.farm.model.entity.ShareCountEntity
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/23 20:16
 *描述:This is ShareViewModel
 */
class ShareViewModel(val remoteRepo: RemoteDataSource,val  localRepo:LocalDataSource): BaseViewModel() {

    val sharecountLiveData = MutableLiveData<ShareCountEntity>()
    val shareContentLiveData = MutableLiveData<ShareContentEntity>()
    val sharefodderLiveData = MutableLiveData<Map<String,Any>>()

    //val memberLiveData = localRepo.getMemberLiveData()
    val yueLiveData = localRepo.getYueLiveData()

    fun getShareCount(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getShareCount()
            }
            val shareContent = withContext(Dispatchers.IO){
                remoteRepo.getShareContent()
            }
            result.checkSuccess {
                sharecountLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
            shareContent.checkSuccess {
                shareContentLiveData.value = it
            }
            shareContent.checkError {
                toast(it.errorMsg?:"${it.errCode}")
            }
        })
    }

    fun getSharefodder(shareType:Int){
        val map = HashMap<String,Int>()
        map["shareType"] = shareType
        val body = MapUtils.map2JsonRequestBody(map)
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getSharefodder(body)
            }

            result.checkSuccess {
                val m = mapOf("shareType" to shareType,"shareCountEntity" to it)
                sharefodderLiveData.value = m
                getYue()
            }
            result.checkError {
                toast(it.errorMsg?:"${it.errCode}")
            }

        },isShowDiaLoading = true)

    }

    fun getYue(){
        viewModelScope.launch {
            val reslut = withContext(Dispatchers.IO){
                remoteRepo.getYue()
            }
            reslut.checkSuccess {
               localRepo.deleteYue()
               localRepo.insertYue(it)
            }
            reslut.checkError {
                toast(it.errorMsg)
            }

        }
    }

}