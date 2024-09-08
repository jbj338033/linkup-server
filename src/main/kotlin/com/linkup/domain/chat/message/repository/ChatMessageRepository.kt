package com.linkup.domain.chat.message.repository

import com.linkup.domain.chat.message.domain.entity.ChatMessage
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository : JpaRepository<ChatMessage, Long>