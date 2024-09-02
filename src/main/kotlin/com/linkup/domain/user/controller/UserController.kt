package com.linkup.domain.user.controller

import com.linkup.domain.user.service.UserService
import com.linkup.global.common.BaseResponse
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
    @GetMapping
    fun getUser(
        @RequestParam(required = false) linkupId: String?,
        @RequestParam(required = false) phoneNumber: String?
    ) = BaseResponse.of(userService.getUser(linkupId, phoneNumber))
}