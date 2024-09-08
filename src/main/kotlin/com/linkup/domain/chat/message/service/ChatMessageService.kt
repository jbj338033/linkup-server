package com.linkup.domain.chat.message.service

import com.linkup.domain.chat.message.dto.request.SendGeneralMessageRequest
import com.linkup.domain.chat.message.dto.request.SendMessageRequest
import com.linkup.domain.chat.message.dto.response.GeneralChatMessageResponse

interface ChatMessageService {
    fun sendMessage(request: SendMessageRequest)

    fun getGeneralMessages(): List<GeneralChatMessageResponse>
    fun sendGeneralMessage(request: SendGeneralMessageRequest)
}