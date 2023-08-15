package com.khoand.demouser.data.remote.api

import com.khoand.demouser.data.remote.model.SvUser

interface ApiHelper {
    suspend fun getUsersFromSever() : List<SvUser>
}