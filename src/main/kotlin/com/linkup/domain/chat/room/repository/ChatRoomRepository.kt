package com.linkup.domain.chat.room.repository

import com.linkup.domain.chat.room.domain.entity.ChatRoom
import com.linkup.domain.chat.room.domain.enums.ChatRoomType
import com.linkup.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ChatRoomRepository : JpaRepository<ChatRoom, UUID> {
    fun findAllByParticipantsUserAndType(user: User, type: ChatRoomType): List<ChatRoom>
}