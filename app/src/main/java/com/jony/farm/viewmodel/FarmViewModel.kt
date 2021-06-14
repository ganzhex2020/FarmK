package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant.KEY_USER_ID
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.combodia.httplib.model.LoadState
import com.combodia.httplib.model.LoadStateType
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.model.entity.KindEntity
import com.jony.farm.model.entity.QueuEntity
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/3/7 20:28
 *描述:This is HomeViewModel
 */
class FarmViewModel(private val remoteRepo: RemoteDataSource, private val localRepo: LocalDataSource) : BaseViewModel() {

    val animalKindLiveData = MutableLiveData<List<KindEntity>>()
    val showAnimalLiveData = MutableLiveData<List<AnimalEntity>>()
    val allAnimalLiveData = MutableLiveData<List<AnimalEntity>>()
   // val balanceLiveData = MutableLiveData<YueEntity>()
    val feedAllStateLiveData = MutableLiveData<HashMap<String,Any>>()
    val feedSingleStateLiveData = MutableLiveData<HashMap<String,Any>>()
    val gatherLiveData = MutableLiveData<List<AnimalEntity>>()
    val queueLiveData = MutableLiveData<QueuEntity>()

    val TOTALAMOUNT = 25

    val memberLiveData = localRepo.getMemberLiveData()

    fun getData(isInit: Boolean) {
        viewModelScope.launch {
            if (isInit) {
                loadState.value = LoadState(LoadStateType.LOADING)
            }

            val farmAnimals = async(Dispatchers.IO) {
                remoteRepo.getFarmAllAnimal()
            }
            val balance = async(Dispatchers.IO) {
                remoteRepo.getYue()
            }

            farmAnimals.await().checkSuccess { animals ->
                //LogUtils.error(animals)
                /**
                 * 分大类
                 */
                val animalSet = HashSet<KindEntity>()
                animals.map {
                    val kindEntity = KindEntity(animalID = it.animalID)
                    animalSet.add(kindEntity)
                }
                val kindList = mutableListOf<KindEntity>()
                kindList.add(KindEntity(animalID = 8))
                kindList.add(KindEntity(animalID = 1))
                kindList.add(KindEntity(animalID = 2))
                kindList.add(KindEntity(animalID = 3))
                kindList.add(KindEntity(animalID = 4))
                kindList.add(KindEntity(animalID = 5))
                kindList.add(KindEntity(animalID = 6))
                kindList.add(KindEntity(animalID = 7))


                animalKindLiveData.value = kindList//animalSet.toList().sortedBy { it.animalID }
                allAnimalLiveData.value = animals
                getFarmAnimal(kindList.first().animalID, animals)
                /*if (animalSet.isNotEmpty()) {
                    getFarmAnimal(animalSet.toList().first().animalID, animals)
                }*/

            }
            balance.await().checkSuccess {
                LogUtils.error("余额1：$it")
                withContext(Dispatchers.IO) {
                    val userId = kv.decodeInt(KEY_USER_ID)
                      localRepo.updateBalance(it!!.item1,it.item2,it.item3,userId)
                  //  localRepo.updateBalance(12345.0, 123.0, 123.0, userId)
                }
            }

            balance.await().checkError {
                toast(it.errorMsg)
            }
            farmAnimals.await().checkError {
                toast(it.errorMsg)
            }
            if (isInit) {
                loadState.value = LoadState(LoadStateType.SUCCESS)
            }

        }
    }

    fun getFarmAnimal(kindId: Int, allList: List<AnimalEntity>) {
        var haveList = allList.filter { it.animalID == kindId }
        val tagList = mutableListOf<Int>()
        for (i in 0 until TOTALAMOUNT) {
            tagList.add(0)
        }
        LogUtils.error("选中的动物数量:" + haveList.size)
        if (haveList.isEmpty()){
            showAnimalLiveData.value = emptyList()
        }
        if (haveList.isNotEmpty()) {
            if (haveList.size >= TOTALAMOUNT - 2) {
                haveList = haveList.subList(0, TOTALAMOUNT - 2)
            }

            var x = 0
            while (x < haveList.size) {
                val random = (2 until TOTALAMOUNT).random()
                if (tagList[random] == 0) {
                    tagList[random] = 1
                    x++
                }
            }
            LogUtils.error("taglist:$tagList")
            x = 0
            val resultList = mutableListOf<AnimalEntity>()
            for (i in 0 until TOTALAMOUNT) {
                if (tagList[i] == 1) {
                    resultList.add(haveList[x])
                    x++
                } else {
                    /**
                     * 空的animal 占位
                     */
                    resultList.add(AnimalEntity())
                }
            }
            showAnimalLiveData.value = resultList
        }

    }

    fun getBalance() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                remoteRepo.getYue()
            }
            result.checkSuccess {
                LogUtils.error("余额2：$it")
                withContext(Dispatchers.IO) {
                    val userId = kv.decodeInt(KEY_USER_ID)
                      localRepo.updateBalance(it.item1,it.item2,it.item3,userId)
                  //  localRepo.updateBalance(22222.0, 2222.0, 2222.0, userId)
                }
            }
            result.checkError {
                LogUtils.error("获取余额失败！！！")
            }
        }
    }

    /**
     *喂养动物
     */
    fun feedAllAnimals(map: Map<String, Int>,feedAnimals:List<AnimalEntity>,FeedType:Int) {
        launchUI(
                {
                    val result = withContext(Dispatchers.IO){
                        remoteRepo.feedAnimals(map)
                    }
                    result.checkSuccess {
                        toast("feed success")
                      //  feedLiveData.value = true
                        val map = HashMap<String,Any>()
                        map["state"] = true
                        map["list"] = feedAnimals
                        map["FeedType"] = FeedType
                        feedAllStateLiveData.value = map
                    }
                    result.checkError {
                        toast(it.errorMsg)
                    }
                    /**
                     * 喂养业务走完获取余额
                     */
                    getBalance()
                },
                isShowDiaLoading = true
        )
    }

    fun feedSingleAnimals(map: Map<String, Int>,singleAnimalEntity: AnimalEntity,FeedType:Int) {
        launchUI(
                {
                    val result = withContext(Dispatchers.IO){
                        remoteRepo.feedAnimals(map)
                    }
                    result.checkSuccess {
                        toast("feed success")
                        //  feedLiveData.value = true
                        val map = HashMap<String,Any>()
                        map["state"] = true
                        map["entity"] = singleAnimalEntity
                        map["FeedType"] = FeedType
                        feedSingleStateLiveData.value = map
                    }
                    result.checkError {
                        toast(it.errorMsg)
                    }
                    /**
                     * 喂养业务走完获取余额
                     */
                    getBalance()
                },
                isShowDiaLoading = true
        )
    }

    fun gather(map: Map<String, Any>, list: List<AnimalEntity>){
        launchUI(
            {
                val body = MapUtils.map2JsonRequestBody(map)
                val result = withContext(Dispatchers.IO){
                    remoteRepo.gather(body)
                }
                if (result.code == 0){
                    gatherLiveData.value = list
                    toast("收获成功")
                }

        },isShowDiaLoading = true)
    }

    fun getQueue(anmID:Int){

        launchUI({
            val result = withContext(Dispatchers.IO){
                remoteRepo.getQueue(anmID)
            }
            result.checkSuccess {
                queueLiveData.value = it
            }
            result.checkError {
                toast(it.errorMsg)
            }

        },isShowDiaLoading = true)

    }

}