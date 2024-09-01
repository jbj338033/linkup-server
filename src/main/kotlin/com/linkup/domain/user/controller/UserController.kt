package com.kakaotalk.domain.user.controller

import com.kakaotalk.domain.user.dto.request.UserUpdateRequest
import com.kakaotalk.domain.user.service.UserService
import com.kakaotalk.global.common.BaseResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "사용자", description = "User")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    fun getMe() = BaseResponse.of(data = userService.getMe())

    @PatchMapping("/me")
    fun updateMe(@RequestBody request: UserUpdateRequest) = BaseResponse.of(data = userService.updateMe(request))
}