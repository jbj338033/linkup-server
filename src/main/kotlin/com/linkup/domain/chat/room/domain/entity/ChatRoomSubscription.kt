package com.linkup.domain.chat.room.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.*

@RedisHash("chat_room_subscription")
class ChatRoomSubscription(
    @Id
    val linkupId: String,

    @Indexed
    val chatRoomId: UUID? = null,

    @Indexed
    val general: Boolean = false
)