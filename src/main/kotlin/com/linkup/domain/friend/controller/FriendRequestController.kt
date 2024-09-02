package com.linkup.domain.friend.controller

import com.linkup.domain.friend.service.FriendRequestService
import com.linkup.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "친구 요청", description = "Friend Request")
@RestController
@RequestMapping("/friend-requests")
class FriendRequestController(
    private val friendRequestService: FriendRequestService
) {
    @Operation(summary = "친구 요청 목록")
    @GetMapping
    fun getFriendRequests() = BaseResponse.of(friendRequestService.getFriendRequests())

    @Operation(summary = "내가 보낸 친구 요청 목록")
    @GetMapping("/sent")
    fun getSentFriendRequests() = BaseResponse.of(friendRequestService.getSentFriendRequests())

    @Operation(summary = "친구 요청")
    @PostMapping
    fun sendFriendRequest(
        @RequestParam(required = false) linkupId: String?,
        @RequestParam(required = false) phoneNumber: String?
    ) =
        BaseResponse.of(friendRequestService.sendFriendRequest(linkupId, phoneNumber))

    @Operation(summary = "친구 요청 수락")
    @PostMapping("/{linkupId}/accept")
    fun acceptFriendRequest(@PathVariable linkupId: String) =
        BaseResponse.of(friendRequestService.acceptFriendRequest(linkupId))

    @Operation(summary = "친구 요청 거절")
    @PostMapping("/{linkupId}/reject")
    fun rejectFriendRequest(@PathVariable linkupId: String) =
        BaseResponse.of(friendRequestService.rejectFriendRequest(linkupId))

    @Operation(summary = "친구 요청 취소")
    @DeleteMapping("/{linkupId}")
    fun cancelFriendRequest(@PathVariable linkupId: String) =
        BaseResponse.of(friendRequestService.cancelFriendRequest(linkupId))
}