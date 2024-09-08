package com.linkup.domain.chat.message.dto.response

data class ChatMessageResponse(
    val id: String,
    val message: String,
    val senderId: String,
    val roomId: String
)