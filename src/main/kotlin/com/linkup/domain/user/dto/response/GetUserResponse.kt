package com.linkup.domain.user.dto.response

import com.linkup.domain.user.domain.entity.User

data class GetUserResponse(
    val nickname: String,
    val profileImage: String,
    val linkupId: String,
    val isFriend: Boolean = false
) {
    companion object {
        fun of(user: User, me: User) = GetUserResponse(
            nickname = user.nickname,
            profileImage = user.profileImage,
            linkupId = user.linkupId,
            isFriend = me.friends.map { it.friend }.contains(user)
        )
    }
}