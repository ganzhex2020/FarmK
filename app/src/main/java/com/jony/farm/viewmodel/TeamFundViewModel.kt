package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.TeamTradeEntity
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/5/7 20:58
 *描述:This is TeamFundViewModel
 */
class TeamFundViewModel(val remoteRepo: RemoteDataSource):BaseViewModel()  {

    val teamtradeLiveData = MutableLiveData<List<TeamTradeEntity>>()

    fun teamcashtrade(pageIndex:Int,map: Map<String,Any>){

        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.teamcashtrade(pageIndex,body)
            }
            result.checkSuccess {
                teamtradeLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }
}