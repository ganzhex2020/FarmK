package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.BankCardEntity
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/26 19:47
 *描述:This is BankCardListViewModel
 */
class BankCardListViewModel(private val remoteRepo: RemoteDataSource):BaseViewModel() {

    val bankcardsLiveData = MutableLiveData<List<BankCardEntity>>()
    val setdefaultLiveData = MutableLiveData<Map<String,Any>>()

    fun getBankCardList(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getBankCardList()
            }
            result.checkSuccess {
                bankcardsLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

    fun setDefaultCard(position:Int,map:Map<String,Any>){
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.setDefaultCard(body)
            }
            result.checkSuccess {
                LogUtils.error("set success")
                val map = HashMap<String,Any>()
                map["position"] = position
                map["state"] = true
                setdefaultLiveData.value = map
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }
}