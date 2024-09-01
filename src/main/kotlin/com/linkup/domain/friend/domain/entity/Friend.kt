package com.linkup.domain.friend.domain.entity

import com.linkup.domain.user.domain.entity.User
import com.linkup.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "friends")
class Friend(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    val friend: User,
) : BaseTimeEntity()