package com.linkup.domain.chat.message.controller

import com.linkup.domain.chat.message.dto.request.SendGeneralMessageRequest
import com.linkup.domain.chat.message.dto.request.SendMessageRequest
import com.linkup.domain.chat.message.service.ChatMessageService
import com.linkup.global.common.BaseResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Tag(name = "채팅 메시지", description = "Chat Message")
@RestController
@RequestMapping("/chat-messages")
class ChatMessageController(
    private val chatMessageService: ChatMessageService
) {
    @MessageMapping("send-message")
    fun sendMessage(@Payload request: SendMessageRequest) {
        chatMessageService.sendMessage(request)
    }

    @MessageMapping("send-general-message")
    fun sendGeneralMessage(@Payload request: SendGeneralMessageRequest) {
        chatMessageService.sendGeneralMessage(request)
    }

    @GetMapping("/general")
    fun getGeneralMessages() = BaseResponse.of(chatMessageService.getGeneralMessages())

    @GetMapping("/{roomId}")
    fun getMessages(@PathVariable roomId: UUID) = BaseResponse.of(chatMessageService.getMessages(roomId))
}