package com.linkup.domain.user.controller

import com.linkup.domain.user.service.UserService
import com.linkup.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "사용자", description = "User")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "사용자 조회", description = "링크업 ID 또는 전화번호로 사용자를 조회합니다.")
    @GetMapping
    fun getUser(
        @RequestParam(required = false) linkupId: String?,
        @RequestParam(required = false) phoneNumber: String?
    ) = BaseResponse.of(userService.getUser(linkupId, phoneNumber))

    @Operation(summary = "사용자 조회", description = "사용자 ID로 사용자를 조회합니다.")
    @GetMapping("/{linkupId}")
    fun getUserByLinkupId(
        @RequestParam linkupId: String
    ) = BaseResponse.of(userService.getUserByLinkupId(linkupId))
}