package com.linkup.domain.auth.service

import com.linkup.domain.auth.dto.request.LoginRequest
import com.linkup.domain.auth.dto.request.ReissueRequest
import com.linkup.domain.auth.dto.request.SignUpRequest
import com.linkup.global.security.jwt.dto.JwtResponse

interface AuthService {
    fun login(request: LoginRequest): JwtResponse
    fun signup(request: SignUpRequest)
    fun reissue(request: ReissueRequest): JwtResponse

    fun checkEmail(email: String): Boolean
    fun checkLinkupId(linkupId: String): Boolean
    fun checkPhoneNumber(phoneNumber: String): Boolean
}