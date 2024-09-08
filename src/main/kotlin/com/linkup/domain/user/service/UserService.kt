package com.linkup.domain.user.service

import com.linkup.domain.user.dto.response.GetUserResponse
import com.linkup.domain.user.dto.response.UserResponse

interface UserService {
    fun getUser(linkupId: String?, phoneNumber: String?): GetUserResponse
    fun getUserByLinkupId(linkupId: String): UserResponse
}