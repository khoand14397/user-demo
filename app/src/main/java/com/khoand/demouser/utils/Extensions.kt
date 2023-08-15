package com.khoand.demouser.utils

import com.khoand.demouser.data.local.entity.User
import com.khoand.demouser.data.remote.model.SvUser

fun svUserToUserLocal(svUser: SvUser): User {
    return User(
        id = svUser.id,
        name = svUser.name,
        email = svUser.email,
        avatar = svUser.avatar
    )
}