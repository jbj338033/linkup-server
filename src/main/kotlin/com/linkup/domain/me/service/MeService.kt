package com.linkup.domain.me.service

import com.linkup.domain.me.dto.request.MeUpdateRequest
import com.linkup.domain.me.dto.response.MeCanChangeLinkupIdResponse
import com.linkup.domain.user.dto.response.UserResponse

interface MeService {
    fun getMe(): UserResponse
    fun updateMe(request: MeUpdateRequest): UserResponse
    fun canChangeLinkupId(): MeCanChangeLinkupIdResponse
}