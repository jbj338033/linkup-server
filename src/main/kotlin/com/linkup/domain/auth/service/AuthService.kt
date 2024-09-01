package com.kakaotalk.domain.auth.service

import com.kakaotalk.domain.auth.dto.request.LoginRequest
import com.kakaotalk.domain.auth.dto.request.ReissueRequest
import com.kakaotalk.domain.auth.dto.request.SignUpRequest
import com.kakaotalk.global.security.jwt.dto.JwtResponse

interface AuthService {
    fun login(request: LoginRequest): JwtResponse
    fun signup(request: SignUpRequest)
    fun reissue(request: ReissueRequest): JwtResponse

    fun checkEmail(email: String): Boolean
    fun checkPhoneNumber(phoneNumber: String): Boolean
}