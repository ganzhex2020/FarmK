package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.TeamMemberEntity
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/5/4 16:01
 *描述:This is TeamViewModel
 */
class TeamViewModel(val remoteRepo: RemoteDataSource):BaseViewModel() {

    val teamLiveData = MutableLiveData<List<TeamMemberEntity>>()

    fun getTeamMemberList(pageSize:Int,pageIndex:Int,layer:Int){
        launchUI({
            val map = HashMap<String,Int>()
            map["layer"] = layer
            val body = MapUtils.map2JsonRequestBody(map)

            val result = withContext(Dispatchers.IO){
                remoteRepo.getTeamMemberList(pageSize, pageIndex,body)
            }
            result.checkSuccess {
                teamLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }
}