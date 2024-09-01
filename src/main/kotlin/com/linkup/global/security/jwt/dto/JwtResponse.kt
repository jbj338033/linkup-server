package com.linkup.global.security.jwt.dto

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)
