package com.linkup.domain.friend.service

import com.linkup.domain.user.dto.response.UserResponse

interface FriendService {

    fun getFriends(): List<UserResponse>
    fun deleteFriend(friendId: String)
}