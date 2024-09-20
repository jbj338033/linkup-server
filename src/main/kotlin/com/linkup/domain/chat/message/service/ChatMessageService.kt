package com.linkup.domain.chat.message.service

import com.linkup.domain.chat.message.dto.request.SendGeneralMessageRequest
import com.linkup.domain.chat.message.dto.request.SendMessageRequest
import com.linkup.domain.chat.message.dto.response.ChatMessageResponse
import com.linkup.domain.chat.message.dto.response.GeneralChatMessageResponse
import java.util.*

interface ChatMessageService {
    fun sendMessage(request: SendMessageRequest)

    fun sendGeneralMessage(request: SendGeneralMessageRequest)
    fun getGeneralMessages(): List<GeneralChatMessageResponse>

    fun getMessages(chatRoomId: UUID): List<ChatMessageResponse>
}