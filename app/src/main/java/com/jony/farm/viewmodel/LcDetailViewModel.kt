package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.AccountDetailEntity
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/5/3 20:57
 *描述:This is FundDetailViewModel
 */
class LcDetailViewModel(val remoteRepo: RemoteDataSource) : BaseViewModel() {
    
    val lcDetailLiveData = MutableLiveData<List<AccountDetailEntity>>()

    fun getLcDetail(pageIndex: Int,index:Int) {

        launchUI({
            val result =if (index == 0){
                val map = HashMap<String,Int>()
                map["tradeType"] = 2
                val body = MapUtils.map2JsonRequestBody(map)
                withContext(Dispatchers.IO) {
                    remoteRepo.getAccountDetail(pageIndex,body)
                }
            }else{
                val map = HashMap<String,String>()
                map["tradeType"] = index.toString()
                val body = MapUtils.map2JsonRequestBody(map)
                withContext(Dispatchers.IO) {
                    remoteRepo.getAccountType(pageIndex,body)
                }
            }
            result.checkSuccess {
                LogUtils.error(it)
                lcDetailLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

}