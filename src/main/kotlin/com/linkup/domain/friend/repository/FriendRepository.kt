package com.linkup.domain.friend.repository

import com.linkup.domain.friend.domain.entity.Friend
import com.linkup.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface FriendRepository : JpaRepository<Friend, Long> {
    fun findAllByUser(user: User): List<Friend>
    fun findAllByFriend(friend: User): List<Friend>
    fun findByUserAndFriend(user: User, friend: User): Friend?
    fun deleteByUserAndFriend(user: User, friend: User)
    fun existsByUserAndFriend(user: User, friend: User): Boolean
}