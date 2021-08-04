package com.jony.farm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jony.farm.model.entity.MemberEntity
import com.jony.farm.model.entity.UserEntity
import com.jony.farm.model.entity.YueEntity

/**
 *Author:ganzhe
 *时间:2020/11/9 19:00
 *描述:This is UserInfoDao
 */

@Dao
interface YueDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYue(yueEntity: YueEntity)


    @Query("SELECT * FROM yueEntity")
    fun getYues():List<YueEntity>
//
    @Query("SELECT * FROM yueEntity")
    fun getYueLiveData():LiveData<List<YueEntity>>
//
    @Query("DELETE FROM yueEntity")
    suspend fun deleteAll()


}