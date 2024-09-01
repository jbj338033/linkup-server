package com.linkup.domain.friend.controller

import com.linkup.domain.friend.service.FriendService
import com.linkup.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "친구", description = "Friend")
@RestController
@RequestMapping("/friends")
class FriendController(
    private val friendService: FriendService
) {
    @Operation(summary = "친구 목록")
    @GetMapping
    fun getFriends() = BaseResponse.of(friendService.getFriends())

    @Operation(summary = "친구 삭제")
    @DeleteMapping("/{friendId}")
    fun deleteFriend(@PathVariable friendId: String) = BaseResponse.of(friendService.deleteFriend(friendId))
}