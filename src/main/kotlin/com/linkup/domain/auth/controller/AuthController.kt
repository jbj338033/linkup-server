package com.kakaotalk.domain.auth.controller

import com.kakaotalk.domain.auth.dto.request.LoginRequest
import com.kakaotalk.domain.auth.dto.request.ReissueRequest
import com.kakaotalk.domain.auth.dto.request.SignUpRequest
import com.kakaotalk.domain.auth.service.AuthService
import com.kakaotalk.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증", description = "Auth")
@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest) = BaseResponse.of(data = authService.login(request))

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest) = BaseResponse.of(data = authService.signup(request), status = 201)

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    fun reissue(@RequestBody request: ReissueRequest) = BaseResponse.of(data = authService.reissue(request), status = 201)

    // 이메일 중복 확인
    @Operation(summary = "이메일 중복 확인")
    @GetMapping("/email")
    fun checkEmail(@RequestParam email: String) = BaseResponse.of(data = authService.checkEmail(email), status = HttpStatus.OK.value())

    // 휴대폰 번호 중복 확인
    @Operation(summary = "휴대폰 번호 중복 확인")
    @GetMapping("/phone-number")
    fun checkPhoneNumber(@RequestParam phoneNumber: String) = BaseResponse.of(data = authService.checkPhoneNumber(phoneNumber), status = HttpStatus.OK.value())
}