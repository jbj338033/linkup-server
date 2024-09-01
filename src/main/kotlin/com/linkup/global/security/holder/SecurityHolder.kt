package com.linkup.global.security.holder

import com.linkup.domain.user.domain.entity.User

interface SecurityHolder {
    val user: User
}