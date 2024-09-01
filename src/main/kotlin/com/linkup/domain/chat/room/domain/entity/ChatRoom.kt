package com.kakaotalk.domain.chat.room.domain.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "chat_rooms")
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String,
)