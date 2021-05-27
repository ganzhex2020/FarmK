package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/5/25 18:42
 *描述:This is SubLineFarmViewModel
 */
class SubFarmViewModel(private val remoteRepo: RemoteDataSource):BaseViewModel() {

    val animalsLiveData = MutableLiveData<List<AnimalEntity>>()

    fun getData(userId:Int){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getSublineFarm(userId)
            }
            result.checkSuccess {
                animalsLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

}