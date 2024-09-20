package com.linkup.domain.chat.room.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "개인 채팅방", description = "Personal Chat Room")
@RestController
@RequestMapping("/chat-rooms/personal")
class PersonalChatRoomController