package com.linkup.domain.friend.repository

import com.linkup.domain.friend.domain.entity.FriendRequest
import com.linkup.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface FriendRequestRepository : JpaRepository<FriendRequest, Long> {
    fun findAllByRequestee(requestee: User): List<FriendRequest>
    fun existsByRequesterAndRequestee(requester: User, requestee: User): Boolean
    fun deleteByRequesterAndRequestee(requester: User, requestee: User)
}