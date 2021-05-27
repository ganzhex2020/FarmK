package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/30 18:30
 *描述:This is InviteViewModel
 */
class InviteViewModel(private val remoteRepo: RemoteDataSource): BaseViewModel()  {

    val linkLiveData = MutableLiveData<String>()

    fun getLinks(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getLinks()
            }
            result.checkSuccess {
                linkLiveData.value = it.url
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }
}