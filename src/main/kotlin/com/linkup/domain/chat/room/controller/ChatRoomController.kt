package com.linkup.domain.chat.room.controller

import com.linkup.domain.chat.room.dto.request.CreateGroupChatRoomRequest
import com.linkup.domain.chat.room.dto.request.CreateOpenChatRoomRequest
import com.linkup.domain.chat.room.dto.request.CreatePersonalChatRoomRequest
import com.linkup.domain.chat.room.service.ChatRoomService
import com.linkup.global.common.BaseResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "채팅방", description = "Chat Room")
@RestController
@RequestMapping("/chat-rooms")
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {
    @GetMapping
    fun getChatRooms() = BaseResponse.of(chatRoomService.getChatRooms())

    @GetMapping("/personal")
    fun getPersonalChatRooms() = BaseResponse.of(chatRoomService.getPersonalChatRooms())

    @GetMapping("/personal/{linkupId}")
    fun getPersonalChatRoom(@PathVariable linkupId: String) =
        BaseResponse.of(chatRoomService.getPersonalChatRoom(linkupId))

    @PostMapping("/personal")
    fun createPersonalChatRoom(@RequestBody request: CreatePersonalChatRoomRequest) =
        BaseResponse.of(chatRoomService.createPersonalChatRoom(request))

    @GetMapping("/group")
    fun getGroupChatRooms() = BaseResponse.of(chatRoomService.getGroupChatRooms())

    @PostMapping("/group")
    fun createGroupChatRoom(@RequestBody request: CreateGroupChatRoomRequest) =
        BaseResponse.of(chatRoomService.createGroupChatRoom(request))

    @GetMapping("/open")
    fun getOpenChatRooms() = BaseResponse.of(chatRoomService.getOpenChatRooms())

    @PostMapping("/open")
    fun createOpenChatRoom(@RequestBody request: CreateOpenChatRoomRequest) =
        BaseResponse.of(chatRoomService.createOpenChatRoom(request))

    @PostMapping("/open/{chatRoomId}/join")
    fun joinOpenChatRoom(@PathVariable chatRoomId: UUID) = BaseResponse.of(chatRoomService.joinOpenChatRoom(chatRoomId))

    @PostMapping("/open/{chatRoomId}/leave")
    fun leaveOpenChatRoom(@PathVariable chatRoomId: UUID) =
        BaseResponse.of(chatRoomService.leaveOpenChatRoom(chatRoomId))
}