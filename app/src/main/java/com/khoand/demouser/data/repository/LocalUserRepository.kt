package com.khoand.demouser.data.repository

import com.khoand.demouser.data.local.AppDatabase
import com.khoand.demouser.data.local.dao.UserDao
import com.khoand.demouser.data.local.entity.User

class LocalUserRepository(private val database: AppDatabase) : UserDao {
    override suspend fun insertAll(listUser: List<User>) = database.userDao().insertAll(listUser)
}