package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamPromoteViewModel(val remoteRepo: RemoteDataSource,val localRepo:LocalDataSource): BaseViewModel() {

    val linkLiveData = MutableLiveData<String>()
    val memberLiveData = localRepo.getMemberLiveData()

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