package com.linkup.domain.friend.service.impl

import com.linkup.domain.friend.error.FriendError
import com.linkup.domain.friend.repository.FriendRepository
import com.linkup.domain.friend.service.FriendService
import com.linkup.domain.user.dto.response.UserResponse
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.error.CustomException
import com.linkup.global.security.holder.SecurityHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FriendServiceImpl(
    private val securityHolder: SecurityHolder,
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
) : FriendService {
    @Transactional(readOnly = true)
    override fun getFriends(): List<UserResponse> {
        val user = securityHolder.user

        return friendRepository.findAllByUser(user).map { UserResponse.of(it.friend) }
    }

    @Transactional
    override fun deleteFriend(linkupId: String) {
        val user = securityHolder.user
        val friend = userRepository.findByLinkupId(linkupId)
            ?: throw CustomException(UserError.USER_NOT_FOUND)

        val isFriend = friendRepository.existsByUserAndFriend(user, friend)

        if (!isFriend) throw CustomException(FriendError.FRIEND_NOT_EXIST)

        friendRepository.deleteByUserAndFriend(user, friend)
        friendRepository.deleteByUserAndFriend(friend, user)
    }
}