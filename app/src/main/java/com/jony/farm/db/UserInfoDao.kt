package com.jony.farm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jony.farm.model.entity.UserEntity

/**
 *Author:ganzhe
 *时间:2020/11/9 19:00
 *描述:This is UserInfoDao
 */

@Dao
interface UserInfoDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userEntity: UserEntity)

    //获取id
    @Query("SELECT userID FROM userEntity")
    fun getUserId(): LiveData<Int>


    @Query("SELECT * FROM userEntity")
    fun getUserInfo():List<UserEntity>
//
    @Query("SELECT * FROM userEntity")
    fun getUserInfoLiveData():LiveData<List<UserEntity>>
//
    @Query("DELETE FROM userEntity")
    suspend fun deleteAll()

    @Query("UPDATE userEntity SET balance = :balance")
    suspend fun setBalance(balance: String): Int

}