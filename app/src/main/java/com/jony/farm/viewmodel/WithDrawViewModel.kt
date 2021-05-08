package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.BankCardEntity
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/26 17:19
 *描述:This is WithDrawViewModel
 */
class WithDrawViewModel(private val remoteRepo: RemoteDataSource):BaseViewModel() {

    val bankcardsLiveData = MutableLiveData<List<BankCardEntity>>()

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

    fun drawMoney(map:Map<String,Any>){
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.drawMoney(body)
            }
            result.checkSuccess {
                toast("提现成功")
            }
            result.checkError {
                toast(it.message)
            }
        },isShowDiaLoading = true)
    }
}