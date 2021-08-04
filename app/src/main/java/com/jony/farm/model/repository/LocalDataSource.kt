package com.jony.farm.model.repository

import androidx.lifecycle.LiveData
import com.jony.farm.app.MyApp
import com.jony.farm.db.AppDatabase
import com.jony.farm.model.entity.MemberEntity
import com.jony.farm.model.entity.UserEntity
import com.jony.farm.model.entity.YueEntity

/**
 *Author:ganzhe
 *时间:2020/12/28 12:39
 *描述:This is LocalDataSource
 */
class LocalDataSource {

    private val appDatabase = AppDatabase.getInstance(MyApp.CONTEXT)
    //private val kv = MMKV.defaultMMKV()
//
    suspend fun insertUserInfo(userEntity: UserEntity){
        appDatabase.userDao().insertUserInfo(userEntity)
    }


    fun getUserInfo(): LiveData<List<UserEntity>> {
       // val userId = kv.decodeInt(KEY_USER_ID)
        return appDatabase.userDao().getUserInfoLiveData()
    }

    suspend fun deleteAllUserInfo(){
        appDatabase.userDao().deleteAll()
    }

    suspend fun insertMember(memberEntity: MemberEntity){
        appDatabase.memberDao().insertMember(memberEntity)
    }

    suspend fun updateBalance(balance: Double,lCoin:Double,fodder:Double,userId:Int){
        appDatabase.memberDao().updateBalance(balance,lCoin,fodder,userId)
    }
    suspend fun updateHeadImg(imgUrl:String,userId: Int){
        appDatabase.memberDao().updateHeadImg(imgUrl, userId)
    }


    fun getMemberLiveData(): LiveData<List<MemberEntity>> {
        return appDatabase.memberDao().getMemberLiveData()
    }

    fun getMembers():List<MemberEntity>{
        return appDatabase.memberDao().getMembers()
    }

    suspend fun deleteAllMember(){
        appDatabase.memberDao().deleteAll()
    }

    suspend fun insertYue(yueEntity: YueEntity){
        appDatabase.yueDao().insertYue(yueEntity)
    }

    suspend fun deleteYue(){
        appDatabase.yueDao().deleteAll()
    }

    fun getYueLiveData(): LiveData<List<YueEntity>> {
        return appDatabase.yueDao().getYueLiveData()
    }

//    //通过获取userId 判断是否登录
//    fun isLogin(): LiveData<Int> {
//        return appDatabase.userDao().getUserId()
//    }
//
//    fun queryAllReadHistory(): Flow<List<Article>> = appDatabase.readHistoryDao().queryAll().map {
//        reversedAllReadHistory(it)
//    }
//
//    private fun reversedAllReadHistory(list: List<ReadHistory>): List<Article> =
//        list.map { it.article.apply { tags = it.tags } }.reversed()
//
//    suspend fun addReadHistory(article: Article) {
//        appDatabase.readHistoryDao().insert(article)
//        article.tags.forEach{
//            appDatabase.readHistoryDao().insertArticleTag(Tag(articleId = article.id,name = it.name,url = it.url))
//        }
//    }






}