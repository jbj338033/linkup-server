package com.linkup.domain.chat.room.dto.response

import com.linkup.domain.chat.room.domain.entity.ChatRoom
import java.util.*

data class ChatRoomResponse(
    val id: UUID,
    val name: String,
    val profileImage: String
) {
    companion object {
        fun of(room: ChatRoom) = ChatRoomResponse(
            id = room.id!!,
            name = room.name,
            profileImage = room.profileImage
        )
    }
}
