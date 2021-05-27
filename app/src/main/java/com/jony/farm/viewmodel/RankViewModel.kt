package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.RankEntity
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/5/17 20:46
 *描述:This is RankViewModel
 */
class RankViewModel(private val remoteRepo: RemoteDataSource):BaseViewModel() {

    val rankLiveData = MutableLiveData<List<RankEntity>>()

    fun getRankData(pageIndex:Int){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getLeaderboard(pageIndex)
            }
            result.checkSuccess {
                //LogUtils.error(it)
                rankLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
                LogUtils.error(it.errorMsg)
            }
        })
    }
}