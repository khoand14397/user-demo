package com.khoand.demouser.data.remote.model

import com.google.gson.annotations.SerializedName

data class SvUser(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)