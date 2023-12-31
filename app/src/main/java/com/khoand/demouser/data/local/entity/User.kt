package com.khoand.demouser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String = "",
    @ColumnInfo("email")
    val email: String = "",
    @ColumnInfo("avatar")
    val avatar: String = ""
)