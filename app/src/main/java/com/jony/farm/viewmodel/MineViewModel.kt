package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.combodia.httplib.config.Constant.KEY_LOGIN_STATE
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/15 14:45
 *描述:This is MineViewModel
 */
class MineViewModel(val remoteRepo:RemoteDataSource,val localRepo:LocalDataSource):BaseViewModel() {

    fun test1(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                remoteRepo.getAnimalLeftCount(2)
            }
            result.checkSuccess {
                LogUtils.error(it)
            }
            result.checkError {
                LogUtils.error(it.errorMsg)
            }
        }
    }
    //val signOutLiveData = MutableLiveData<Boolean>()
    // val userInfoLiveData = localrepo.getUserInfo()
    val memberLiveData = localRepo.getMemberLiveData()
    val yueLiveData = localRepo.getYueLiveData()

    val unReadLiveData = MutableLiveData<Double>()

    fun getData(){
        viewModelScope.launch {
            val reslut = withContext(Dispatchers.IO){
                remoteRepo.getMembers()
            }
            val yue = withContext(Dispatchers.IO){
                remoteRepo.getYue()
            }
            reslut.checkSuccess {
                withContext(Dispatchers.IO){
                    localRepo.insertMember(it.copy(password = kv.decodeString(Constant.KEY_USER_PWD)))
                }
            }
            yue.checkSuccess {
                localRepo.deleteYue()
                localRepo.insertYue(it)
            }
            reslut.checkError {
                toast(it.errorMsg)
            }

        }
    }

    /*fun signOut(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.signOut()
            }
            result.checkSuccess {
                *//**
                 * 退出登录 修改缓存登录标志
                 *//*
                kv.encode(KEY_LOGIN_STATE,false)

                signOutLiveData.value = true
            }
        },isShowDiaLoading = true)
    }*/

    fun getMsgList(){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getZnMsg(1,15)
            }
            result.checkSuccess {
                unReadLiveData.value = result.extendData as Double
            }
        })
    }
}