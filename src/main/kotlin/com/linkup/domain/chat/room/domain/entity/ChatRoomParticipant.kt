package com.linkup.domain.chat.room.domain.entity

import com.linkup.domain.user.domain.entity.User
import com.linkup.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "chat_room_participants")
class ChatRoomParticipant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    val room: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "last_read_message_id")
    var lastReadMessageId: Long? = null,
) : BaseTimeEntity()