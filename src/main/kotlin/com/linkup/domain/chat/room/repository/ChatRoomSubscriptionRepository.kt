package com.linkup.domain.chat.room.repository

import com.linkup.domain.chat.room.domain.entity.ChatRoomSubscription
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ChatRoomSubscriptionRepository : CrudRepository<ChatRoomSubscription, String> {
    fun findAllByChatRoomId(chatRoomId: UUID): List<ChatRoomSubscription>
}