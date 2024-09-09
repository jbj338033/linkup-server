package com.linkup.domain.auth.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("refresh_token")
class RefreshToken(
    @Id
    val email: String,

    @Indexed
    val refreshToken: String
)