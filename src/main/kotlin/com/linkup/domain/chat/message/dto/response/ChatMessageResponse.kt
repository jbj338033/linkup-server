package com.linkup.domain.chat.message.dto.response

import com.linkup.domain.chat.message.domain.entity.ChatMessage
import com.linkup.domain.user.domain.entity.User

data class ChatMessageResponse(
    val id: String,
    val content: String?,
    val sender: ChatMessageSenderResponse,
) {
    companion object {
        fun of(message: ChatMessage) = ChatMessageResponse(
            id = message.id.toString(),
            content = message.content,
            sender = ChatMessageSenderResponse.of(message.sender)
        )
    }
}

data class ChatMessageSenderResponse(
    val linkupId: String,
    val nickname: String,
    val profileImage: String,
) {
    companion object {
        fun of(sender: User) = ChatMessageSenderResponse(
            linkupId = sender.linkupId,
            nickname = sender.nickname,
            profileImage = sender.profileImage,
        )
    }
}