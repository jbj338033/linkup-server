package com.linkup.domain.chat.room.domain.entity

import com.linkup.domain.chat.message.domain.entity.ChatMessage
import com.linkup.domain.chat.room.domain.enums.ChatRoomType
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
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: ChatRoomType,

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val participants: MutableSet<ChatRoomParticipant> = mutableSetOf(),

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val messages: MutableSet<ChatMessage> = mutableSetOf(),

    @Column(name = "profile_image", nullable = false)
    var profileImage: String
) : BaseTimeEntity()