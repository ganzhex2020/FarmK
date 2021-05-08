package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/28 18:14
 *描述:This is AnimalHistoryViewModel
 */
class AnimalHistoryViewModel(private val remoteRepo: RemoteDataSource): BaseViewModel() {
    
    val animalHistoryLiveData = MutableLiveData<List<AnimalEntity>>()

    fun getAnimalHistory(pageIndex:Int,pageSize:Int){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getAnimalHistory(pageSize, pageIndex)
            }
            result.checkSuccess {

                animalHistoryLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })


    }

}