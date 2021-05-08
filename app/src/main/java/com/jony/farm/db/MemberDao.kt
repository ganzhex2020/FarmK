package com.jony.farm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jony.farm.model.entity.MemberEntity
import com.jony.farm.model.entity.UserEntity

/**
 *Author:ganzhe
 *时间:2020/11/9 19:00
 *描述:This is UserInfoDao
 */

@Dao
interface MemberDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(memberEntity: MemberEntity)

    //获取id
    @Query("SELECT userID FROM memberEntity")
    fun getUserId(): LiveData<Int>


    @Query("SELECT * FROM memberEntity")
    fun getMembers():List<MemberEntity>
//
    @Query("SELECT * FROM memberEntity")
    fun getMemberLiveData():LiveData<List<MemberEntity>>
//
    @Query("DELETE FROM memberEntity")
    suspend fun deleteAll()

    @Transaction
    @Query("UPDATE memberEntity SET balance = :balance,lCoin = :lCoin,fodder = :fodder WHERE userID = :userId")
    suspend fun updateBalance(balance: Double,lCoin:Double,fodder:Double,userId:Int)


    @Transaction
    @Query("UPDATE memberEntity SET headImg = :imgUrl WHERE userID = :userId")
    suspend fun updateHeadImg(imgUrl: String,userId:Int)

}