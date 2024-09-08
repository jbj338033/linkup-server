package com.linkup.domain.chat.message.domain.entity

import com.linkup.domain.user.domain.entity.User
import com.linkup.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "general_chat_messages")
class GeneralChatMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "content", nullable = false)
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    val sender: User,
): BaseTimeEntity()