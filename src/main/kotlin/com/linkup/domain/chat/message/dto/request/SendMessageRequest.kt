package com.linkup.domain.chat.message.dto.request

import java.util.*

data class SendMessageRequest(
    val roomId: UUID,
    val content: String,
    val mentions: List<String>,
)