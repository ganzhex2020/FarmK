package com.jony.farm.viewmodel

import androidx.lifecycle.viewModelScope
import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.config.Const.IMAGE_URL
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.util.MapJUtil
import com.jony.farm.util.MapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 *Author:ganzhe
 *时间:2021/4/16 17:11
 *描述:This is UserInfoViewModel
 */
class UserInfoViewModel(val remoteRepo: RemoteDataSource, private val localRepo: LocalDataSource) :
    BaseViewModel() {
    
    val memberLiveData = localRepo.getMemberLiveData()
    fun getUserInfo() {
        viewModelScope.launch {
            val reslut = withContext(Dispatchers.IO) {
                remoteRepo.getMembers()
            }
            reslut.checkSuccess {

                withContext(Dispatchers.IO) {
                    localRepo.insertMember(it.copy(password = kv.decodeString(Constant.KEY_USER_PWD)))
                }
            }
            reslut.checkError {
                toast(it.errorMsg)
            }

        }
    }

    fun updateUserInfo(map: Map<String, Any>) {
        launchUI({
            val body = MapUtils.map2JsonRequestBody(map)
            val result = withContext(Dispatchers.IO) {
                remoteRepo.updateUserInfo(body)
            }
            result.checkSuccess {
                toast("更新个人信息成功")

                val member = withContext(Dispatchers.IO) {
                    remoteRepo.getMembers()
                }
                member.checkSuccess {

                    withContext(Dispatchers.IO) {
                        localRepo.insertMember(it.copy(password = kv.decodeString(Constant.KEY_USER_PWD)))
                    }
                }
            }
            result.checkError {
                toast(it.errorMsg)
            }
        }, isShowDiaLoading = true)
    }

    fun uploadHead(path: String) {
        launchUI({
            // val requestBody = MapUtils.file2RequestBody(path,"file")
            val file = File(path)
            val fileBody = /*MapUtils.file2MultipartBody(path,"file")*/file.asRequestBody("image/png".toMediaType())
            val part: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, fileBody)
            val typeBody: RequestBody = MapJUtil.getRequestBody("userHeadImg","form-data")
            val result = withContext(Dispatchers.IO) {
                remoteRepo.uploadHead1(IMAGE_URL, typeBody, part)
            }
            result.checkSuccess {
                val map = HashMap<String,String>()
                map["imgurl"] = it
                val body = MapUtils.map2JsonRequestBody(map)
                val data = withContext(Dispatchers.IO){
                    remoteRepo.uploadHead2(body)
                }
                data.checkSuccess { imgUrl ->
                    LogUtils.error(imgUrl)
                    toast("上传头像成功")
                    kv.encode(Constant.KEY_USER_AVATAR,imgUrl)
                    localRepo.updateHeadImg(imgUrl,kv.decodeInt(Constant.KEY_USER_ID))
                }
                data.checkError { ex->
                    toast(ex.errorMsg)
                }
            }
            result.checkError {
                LogUtils.error(it.errorMsg)
            }


        }, isShowDiaLoading = true)
    }
}