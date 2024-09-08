package com.linkup.domain.chat.message.repository

import com.linkup.domain.chat.message.domain.entity.GeneralChatMessage
import org.springframework.data.jpa.repository.JpaRepository

interface GeneralChatMessageRepository: JpaRepository<GeneralChatMessage, Long> {
}