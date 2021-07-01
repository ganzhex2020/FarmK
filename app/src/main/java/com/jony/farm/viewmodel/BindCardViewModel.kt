package com.jony.farm.viewmodel

import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/27 15:02
 *描述:This is BindCardViewModel
 */
class BindCardViewModel(private val remoteRepo: RemoteDataSource,private val localRepo:LocalDataSource): BaseViewModel() {

    val memberLiveData = localRepo.getMemberLiveData()

    fun bindCard(map:Map<String,Any>){
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.createBankCard(body)
            }
            result.checkSuccess {
                toast("添加银行卡成功")
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }

    fun modifyCard(map: Map<String, Any>){
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.modifyCard(body)
            }
            result.checkSuccess {
                toast("修改成功")
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }

}