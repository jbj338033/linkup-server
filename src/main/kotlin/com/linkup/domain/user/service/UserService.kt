package com.kakaotalk.domain.user.service

import com.kakaotalk.domain.user.dto.request.UserUpdateRequest
import com.kakaotalk.domain.user.dto.response.UserResponse

interface UserService {
    fun getMe(): UserResponse
    fun updateMe(request: UserUpdateRequest): UserResponse
}