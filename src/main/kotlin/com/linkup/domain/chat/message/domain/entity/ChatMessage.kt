package com.kakaotalk.domain.chat.message.domain.entity

import com.kakaotalk.domain.chat.room.domain.entity.ChatRoom
import com.kakaotalk.domain.user.domain.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "chat_messages")
class ChatMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "content", nullable = false)
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    val room: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    val sender: User
)