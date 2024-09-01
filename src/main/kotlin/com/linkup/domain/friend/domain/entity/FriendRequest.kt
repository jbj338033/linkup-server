package com.linkup.domain.friend.domain.entity

import com.linkup.domain.user.domain.entity.User
import com.linkup.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "friend_requests")
class FriendRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    val requester: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestee_id")
    val requestee: User,
) : BaseTimeEntity()