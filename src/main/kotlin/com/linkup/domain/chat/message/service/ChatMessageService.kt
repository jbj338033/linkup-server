package com.linkup.domain.chat.message.service

import com.linkup.domain.chat.message.dto.request.SendMessageRequest

interface ChatMessageService {
    fun sendMessage(request: SendMessageRequest)
}