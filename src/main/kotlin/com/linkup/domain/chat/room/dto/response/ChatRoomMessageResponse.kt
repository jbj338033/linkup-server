package com.linkup.domain.chat.room.dto.response

import com.linkup.domain.user.domain.entity.User

data class ChatRoomMessageResponse(
    val content: String?,
    val sender: ChatRoomMessageSenderResponse,
)

data class ChatRoomMessageSenderResponse(
    val profileImage: String,
    val nickname: String,
    val linkupId: String
) {
    companion object {
        fun of(user: User) = ChatRoomMessageSenderResponse(
            profileImage = user.profileImage,
            nickname = user.nickname,
            linkupId = user.linkupId
        )
    }
}