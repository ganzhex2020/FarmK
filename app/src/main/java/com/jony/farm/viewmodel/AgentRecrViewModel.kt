package com.jony.farm.viewmodel

import com.combodia.basemodule.base.BaseViewModel
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.config.Constant
import com.combodia.httplib.ext.checkError
import com.combodia.httplib.ext.checkSuccess
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AgentRecrViewModel(private val remoteRepo: RemoteDataSource,private val localRepo:LocalDataSource): BaseViewModel() {



        fun tobeAgent(){
            launchUI({
                val result = withContext(Dispatchers.IO){
                    remoteRepo.tobeAgent()
                }
                result.checkSuccess {
                    val member = withContext(Dispatchers.IO){
                        remoteRepo.getMembers()
                    }
                    member.checkSuccess {
                        withContext(Dispatchers.IO) {
                            localRepo.insertMember(it.copy(password = kv.decodeString(Constant.KEY_USER_PWD)))
                        }
                        toast("成为会员成功")
                    }
                }

                result.checkError {
                    toast(it.errorMsg)
                }
            },isShowDiaLoading = true)
        }

}