package com.kakaotalk.domain.chat.message.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "채팅 메시지", description = "Chat Message")
@RestController
@RequestMapping("/chat/messages")
class ChatMessageController {
}