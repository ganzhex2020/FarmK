package com.jony.farm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:ganzhe
 *时间:2021/4/29 18:30
 *描述:This is ChangePwdViewModel
 */
class ChangePwdViewModel(private val remoteRepo: RemoteDataSource): BaseViewModel() {

    val updateLiveData = MutableLiveData<Boolean>()

    fun updatePwd(map: Map<String,Any>){
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO){
                remoteRepo.updatePwd(body)
            }
            result.checkSuccess {
                toast("修改密码成功")
                updateLiveData.value = true
                //修改密码成功后  再次调用用户相关接口会403
               /* val result = withContext(Dispatchers.IO){
                    remoteRepo.signOut()
                }*/


            }
            result.checkError {
                toast(it.errorMsg)
                LogUtils.error(it.errorMsg)
            }
        },isShowDiaLoading = true)

    }
}