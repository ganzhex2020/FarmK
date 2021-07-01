package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.AccountDetailEntity
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/29 17:57
 *描述:This is FodderDetailViewModel
 */
class FodderDetailViewModel(val remoteRepo: RemoteDataSource):BaseViewModel()  {

    val accountDetailLiveData = MutableLiveData<List<AccountDetailEntity>>()

    fun getFodderDetail(pageIndex:Int){
        launchUI({
            val map = HashMap<String,Int>()
            map["tradeType"] = 3
            val body = MapUtils.map2JsonRequestBody(map)

            val result = withContext(Dispatchers.IO) {
                remoteRepo.getAccountDetail(pageIndex,body)
            }
            result.checkSuccess {
                accountDetailLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

}