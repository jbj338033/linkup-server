package com.linkup.domain.chat.room.dto.request

data class CreateGroupChatRoomRequest(
    val name: String,
    val linkupIds: List<String>,
    val profileImage: String?
)
