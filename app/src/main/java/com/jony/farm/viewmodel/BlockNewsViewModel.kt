package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.entity.NewsEntity
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/5/17 19:20
 *描述:This is BlockNewsViewModel
 */
class BlockNewsViewModel(private val remoteRepo: RemoteDataSource):BaseViewModel() {

    val newsListLiveData = MutableLiveData<List<NewsEntity>>()
    val newDetailLiveData = MutableLiveData<NewsEntity>()

    fun getNewsList(pageIndex:Int){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getNewsList(pageIndex)
            }
            result.checkSuccess {
                newsListLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

    fun getNewsDetail(Id:Int){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getNewsDetail(Id)
            }
            result.checkSuccess {
                newDetailLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }
}