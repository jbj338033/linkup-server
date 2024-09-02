package com.linkup.domain.chat.room.repository

import com.linkup.domain.chat.room.domain.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ChatRoomRepository : JpaRepository<ChatRoom, UUID> {
}