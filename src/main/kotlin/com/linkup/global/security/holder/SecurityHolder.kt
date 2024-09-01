package com.kakaotalk.global.security.holder

import com.kakaotalk.domain.user.domain.entity.User

interface SecurityHolder {
    val user: User
}