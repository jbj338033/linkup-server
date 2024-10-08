package com.linkup.domain.friend.service

import com.linkup.domain.user.dto.response.UserResponse

interface FriendRequestService {
    fun getFriendRequests(): List<UserResponse>
    fun getSentFriendRequests(): List<UserResponse>

    fun sendFriendRequest(linkupId: String?, phoneNumber: String?)
    fun acceptFriendRequest(linkupId: String)
    fun rejectFriendRequest(linkupId: String)
    fun cancelFriendRequest(linkupId: String)
}