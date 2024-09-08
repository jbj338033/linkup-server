package com.linkup.domain.chat.message.dto.response

import com.linkup.domain.chat.message.domain.entity.GeneralChatMessage
import com.linkup.domain.user.domain.entity.User

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
    val content: String,
    val sender: GeneralChatMessageSenderResponse
) {
    companion object {
        fun of(message: GeneralChatMessage) = GeneralChatMessageResponse(
            content = message.content,
            sender = GeneralChatMessageSenderResponse.of(message.sender)
        )
    }
}