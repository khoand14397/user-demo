package com.khoand.demouser.data.repository

import com.khoand.demouser.data.remote.api.ApiHelper
import com.khoand.demouser.data.remote.api.ApiService
import com.khoand.demouser.data.remote.model.SvUser

class SvUserRepository(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUsersFromSever(): List<SvUser> = apiService.getUsers()
}