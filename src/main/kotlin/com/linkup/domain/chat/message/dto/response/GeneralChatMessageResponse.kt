package com.linkup.domain.chat.message.dto.response

import com.linkup.domain.chat.message.domain.entity.GeneralChatMessage
import com.linkup.domain.user.domain.entity.User
import java.time.LocalDateTime

data class GeneralChatMessageSenderResponse(
    val nickname: String,
    val profileImage: String,
    val linkupId: String
) {
    companion object {
        fun of(sender: User) = GeneralChatMessageSenderResponse(
            nickname = sender.nickname,
            profileImage = sender.profileImage,
            linkupId = sender.linkupId
        )
    }
}

data class GeneralChatMessageResponse(
    val id: Long,
    val content: String,
    val sender: GeneralChatMessageSenderResponse,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(message: GeneralChatMessage) = GeneralChatMessageResponse(
            id = message.id!!,
            content = message.content,
            sender = GeneralChatMessageSenderResponse.of(message.sender),
            createdAt = message.createdAt,
            updatedAt = message.updatedAt
        )
    }
}