package com.linkup.domain.user.service

import com.linkup.domain.user.dto.response.GetUserResponse

interface UserService {
    fun getUser(linkupId: String?, phoneNumber: String?): GetUserResponse
}