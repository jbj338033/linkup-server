package com.linkup.domain.user.controller

import com.linkup.domain.user.dto.request.UserUpdateRequest
import com.linkup.domain.user.service.UserService
import com.linkup.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "사용자", description = "User")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "나 조회")
    @GetMapping("/me")
    fun getMe() = BaseResponse.of(data = userService.getMe())

    @Operation(summary = "나 수정")
    @PatchMapping("/me")
    fun updateMe(@RequestBody request: UserUpdateRequest) = BaseResponse.of(data = userService.updateMe(request))
}