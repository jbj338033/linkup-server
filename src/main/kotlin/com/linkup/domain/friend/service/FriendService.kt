package com.linkup.domain.friend.service

import com.linkup.domain.user.dto.response.UserResponse

interface FriendRequestService {
    fun sendFriendRequest(requesteeId: String)
    fun acceptFriendRequest(requesterId: String)
    fun rejectFriendRequest(requesterId: String)
    fun cancelFriendRequest(requesteeId: String)

    fun getFriendRequests(): List<UserResponse>

    fun getFriends(): List<UserResponse>
}