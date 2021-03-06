package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.combodia.httplib.model.LoadState
import com.combodia.httplib.model.LoadStateType
import com.jony.farm.R
import com.jony.farm.config.Const

import com.jony.farm.model.entity.KindEntity
import com.jony.farm.model.entity.BannerEntity
import com.jony.farm.model.entity.HashRateEntity
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zlc.season.claritypotion.ClarityPotion

/**
 *Author:ganzhe
 *时间:2021/3/7 20:28
 *描述:This is HomeViewModel
 */
class MarketViewModel(private val remoteRepo: RemoteDataSource) : BaseViewModel() {

    val hashRateLiveData = MutableLiveData<HashRateEntity>()
  //  val bannerLiveData = MutableLiveData<List<BannerEntity>>()
    val animalLiveData = MutableLiveData<List<KindEntity>>()
    val animalLeftLiveData = MutableLiveData<HashMap<String, Int>>()
    val buyStateLiveData = MutableLiveData<Boolean>()


    fun getData(isInit: Boolean) {
        if (isInit) {
            loadState.value = LoadState(LoadStateType.LOADING)
        }
        viewModelScope.launch {

           /* val banners = async(Dispatchers.IO) {
                remoteRepo.getBanner(Const.APP_ID)
            }*/
            val hashRate = async(Dispatchers.IO){
                remoteRepo.getHashRateAll()
            }
            val animalList = async(Dispatchers.IO) {
                remoteRepo.getAnimalList()
            }
           /* banners.await().checkSuccess {
                bannerLiveData.value = it
            }

            banners.await().checkError {
                LogUtils.error(it.errorMsg)
                toast(it.errorMsg)
            }*/
            hashRate.await().checkSuccess {
                hashRateLiveData.value = it
            }
            hashRate.await().checkError {
                LogUtils.error(it.errorMsg)
                toast(it.errorMsg)
            }
            animalList.await().checkSuccess {
                animalLiveData.value = it
            }
            animalList.await().checkError {
                LogUtils.error(it.errorMsg)
                toast(it.errorMsg)
            }
            if (isInit) {
                loadState.value = LoadState(LoadStateType.SUCCESS)
            }
        }
    }

    fun getAnimalList(){
        viewModelScope.launch {
            val animalList = withContext(Dispatchers.IO) {
                remoteRepo.getAnimalList()
            }
            animalList.checkSuccess {
                animalLiveData.value = it
            }
        }
    }

    fun getAnimalLeft(animalId: Int) {
        launchUI({
            val farmAnimals = withContext(Dispatchers.IO) {
                remoteRepo.getFarmAllAnimal()
            }
            val result = withContext(Dispatchers.IO) {
                remoteRepo.getAnimalLeft(animalId)
            }
            var haveCount =0
            farmAnimals.checkSuccess { list ->
                val all = list.filter { it.animalID == animalId }
                haveCount = all.size
            }
            result.checkSuccess { leftCount ->
                //动物剩下的大于0
                if (leftCount>0){
                    val map = HashMap<String, Int>()
                    map["animalId"] = animalId
                    map["leftCount"] = leftCount
                    map["haveCount"] = haveCount
                    animalLeftLiveData.value = map
                }else{
                    //否则重新请求recy的数据 刷新页面
                    val animalList = withContext(Dispatchers.IO) {
                        remoteRepo.getAnimalList()
                    }
                    animalList.checkSuccess {
                        animalLiveData.value = it
                    }

                }

            }
            result.checkError {
                toast(it.errorMsg)
            }
        })
    }

    fun buyAnimal(animalId: Int,count:Int){
        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.buyAnimal(animalId, count)
            }
            result.checkSuccess {
                toast(ClarityPotion.clarityPotion.getString(R.string.buy_success))
                buyStateLiveData.value = true
            }
            result.checkError {
                toast(it.errorMsg)
            }
        },isShowDiaLoading = true)
    }

}