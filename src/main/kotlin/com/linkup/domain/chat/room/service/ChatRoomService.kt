package com.linkup.domain.chat.room.service

import com.linkup.domain.chat.room.dto.request.CreateGroupChatRoomRequest
import com.linkup.domain.chat.room.dto.request.CreateOpenChatRoomRequest
import com.linkup.domain.chat.room.dto.request.CreatePersonalChatRoomRequest
import com.linkup.domain.chat.room.dto.response.ChatRoomResponse
import java.util.*

interface ChatRoomService {
    fun getChatRooms(): List<ChatRoomResponse>

    fun getPersonalChatRooms(): List<ChatRoomResponse>
    fun getPersonalChatRoom(linkupId: String): ChatRoomResponse
    fun createPersonalChatRoom(request: CreatePersonalChatRoomRequest): ChatRoomResponse

    fun getGroupChatRooms(): List<ChatRoomResponse>
    fun createGroupChatRoom(request: CreateGroupChatRoomRequest): ChatRoomResponse

    fun getOpenChatRooms(): List<ChatRoomResponse>
    fun createOpenChatRoom(request: CreateOpenChatRoomRequest): ChatRoomResponse
    fun joinOpenChatRoom(chatRoomId: UUID): ChatRoomResponse
    fun leaveOpenChatRoom(chatRoomId: UUID): ChatRoomResponse

    fun subscribeChatRoom(chatRoomId: UUID)
    fun subscribeGeneralChatRoom()
    fun unsubscribeChatRoom()
}