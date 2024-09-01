package com.linkup.domain.user.service

import com.linkup.domain.user.dto.request.UserUpdateRequest
import com.linkup.domain.user.dto.response.UserResponse

interface UserService {
    fun getMe(): UserResponse
    fun updateMe(request: UserUpdateRequest): UserResponse
}