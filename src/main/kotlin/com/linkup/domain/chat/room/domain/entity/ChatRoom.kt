package com.linkup.domain.chat.room.domain.entity

import com.linkup.domain.chat.message.domain.entity.ChatMessage
import com.linkup.global.common.BaseTimeEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "chat_rooms")
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    val members: Set<ChatRoomMember> = mutableSetOf(),

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    val messages: Set<ChatMessage> = mutableSetOf()
) : BaseTimeEntity()