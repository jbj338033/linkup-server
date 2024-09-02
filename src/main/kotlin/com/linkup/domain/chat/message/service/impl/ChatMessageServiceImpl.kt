package com.linkup.domain.chat.message.service.impl

import com.linkup.domain.chat.message.dto.request.SendMessageRequest
import com.linkup.domain.chat.message.service.ChatMessageService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class ChatMessageServiceImpl(
    private val rabbitTemplate: RabbitTemplate
) : ChatMessageService {
    override fun sendMessage(request: SendMessageRequest) {
        rabbitTemplate.convertAndSend("message", request)
    }
}