package com.khoand.demouser.data.remote.api

import com.khoand.demouser.data.remote.model.SvUser
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<SvUser>
}