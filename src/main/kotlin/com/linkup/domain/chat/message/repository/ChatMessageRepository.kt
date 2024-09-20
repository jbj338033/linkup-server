package com.linkup.domain.chat.message.repository

import com.linkup.domain.chat.message.domain.entity.ChatMessage
import com.linkup.domain.chat.room.domain.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
    fun findAllByRoom(room: ChatRoom): List<ChatMessage>
}