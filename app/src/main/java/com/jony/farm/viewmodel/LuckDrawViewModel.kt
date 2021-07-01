package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.LuckResultEntity
import com.jony.farm.model.entity.ShareCountEntity
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/2 16:29
 *描述:This is RotaryViewModel
 */
class LuckDrawViewModel(private val remoteRepo: RemoteDataSource): BaseViewModel() {

    val sharecountLiveData = MutableLiveData<ShareCountEntity>()
    val sharefodderLiveData = MutableLiveData<Map<String,Any>>()
    val luckResultLiveData = MutableLiveData<List<LuckResultEntity>>()

    fun getShareCount(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getShareCount()
            }
            result.checkSuccess {
                sharecountLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

    /**
     * 开始获取转盘的结果
     */
    fun getSharefodder(){
        val map = HashMap<String,Int>()
        map["shareType"] = 5
        val body = MapUtils.map2JsonRequestBody(map)
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getSharefodder(body)
            }
            result.checkSuccess {
                val m = mapOf("num" to (result.extendData as Double).toInt(),"shareCountEntity" to it)
                sharefodderLiveData.value = m
            }
            result.checkError {
                toast(it.errorMsg?:"${it.errCode}")
            }
        })

    }

    fun getLuckResult(pageIndex:Int){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getLuckREsult(pageIndex)
            }
            result.checkSuccess {
                luckResultLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = pageIndex==1)
    }


}