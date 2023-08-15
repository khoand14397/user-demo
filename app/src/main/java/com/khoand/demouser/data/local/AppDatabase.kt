package com.khoand.demouser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khoand.demouser.data.local.AppDatabase.Companion.DB_VERSION
import com.khoand.demouser.data.local.dao.UserDao
import com.khoand.demouser.data.local.entity.User

@Database(entities = [User::class], version = DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "databases"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}