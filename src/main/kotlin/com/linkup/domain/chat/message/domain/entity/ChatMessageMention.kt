package com.linkup.domain.chat.message.domain.entity

import com.linkup.domain.chat.room.domain.entity.ChatRoomMember
import com.linkup.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "chat_message_mentions")
class ChatMessageMention(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    val message: ChatMessage,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_member_id", nullable = false)
    val member: ChatRoomMember
) : BaseTimeEntity()