package com.jony.farm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jony.farm.model.entity.MemberEntity
import com.jony.farm.model.entity.UserEntity
import com.jony.farm.model.entity.YueEntity

/**
 *Author:ganzhe
 *时间:2020/11/9 16:05
 *描述:This is AppDatabase
 */

@Database(entities = [UserEntity::class,MemberEntity::class,YueEntity::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserInfoDao
    abstract fun memberDao(): MemberDao
    abstract fun yueDao(): YueDao


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context:Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "farm.db"
            )
                .build()
    }
}