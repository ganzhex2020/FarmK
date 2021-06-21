package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.config.Const
import com.jony.farm.model.entity.BannerEntity
import com.jony.farm.model.entity.CompanyEntity
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/5/17 15:26
 *描述:This is HomeViewModel
 */
class HomeViewModel(private val remoteRepo: RemoteDataSource,private val localRepo:LocalDataSource):BaseViewModel() {

    val bannerLiveData = MutableLiveData<List<BannerEntity>>()
    val companyLiveData = MutableLiveData<CompanyEntity>()
    val isAgentLiveData = MutableLiveData<Boolean>()

    fun getData(){
        viewModelScope.launch {
            val banners = async(Dispatchers.IO) {
                remoteRepo.getBanner(Const.APP_ID)
            }

            val company = async(Dispatchers.IO){
                remoteRepo.getCompany(Const.APP_ID)
            }

            banners.await().checkSuccess {
                bannerLiveData.value = it
            }
            banners.await().checkError {
                LogUtils.error(it.errorMsg)
                toast(it.errorMsg)
            }
            company.await().checkSuccess {
                companyLiveData.value = it
            }
            company.await().checkError {
                toast(it.errorMsg)
            }

        }
    }

    fun isAgent(){
        viewModelScope.launch {
            val members = withContext(Dispatchers.IO){
                localRepo.getMembers()
            }
            members.filter { it.userID == MMKV.defaultMMKV().decodeInt(Constant.KEY_USER_ID) }
                .map {
                    isAgentLiveData.value = it.userType == 2
                }

        }

    }
}