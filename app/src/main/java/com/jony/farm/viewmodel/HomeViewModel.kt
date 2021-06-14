package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.config.Const
import com.jony.farm.model.entity.BannerEntity
import com.jony.farm.model.entity.CompanyEntity
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *Author:ganzhe
 *时间:2021/5/17 15:26
 *描述:This is HomeViewModel
 */
class HomeViewModel(private val remoteRepo: RemoteDataSource):BaseViewModel() {

    val bannerLiveData = MutableLiveData<List<BannerEntity>>()
    val companyLiveData = MutableLiveData<CompanyEntity>()

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
}