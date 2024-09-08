package com.linkup.domain.user.dto.response

data class GetUserResponse(
    val nickname: String,
    val profileImage: String,
    val linkupId: String,
    val isFriend: Boolean,
    val isFriendRequestSent: Boolean,
)