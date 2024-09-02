package com.linkup.domain.me.controller

import com.linkup.domain.me.dto.request.MeUpdateRequest
import com.linkup.domain.me.service.MeService
import com.linkup.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "나", description = "Me")
@RestController
@RequestMapping("/me")
class MeController(
    private val meService: MeService
) {
    @Operation(summary = "나 조회")
    @GetMapping
    fun getMe() = BaseResponse.of(data = meService.getMe())

    @Operation(summary = "나 수정")
    @PatchMapping
    fun updateMe(@RequestBody request: MeUpdateRequest) = BaseResponse.of(data = meService.updateMe(request))

    @Operation(summary = "링크업 아이디 변경 가능 여부")
    @GetMapping("/can-change-linkup-id")
    fun canChangeLinkupId() = BaseResponse.of(data = meService.canChangeLinkupId())
}