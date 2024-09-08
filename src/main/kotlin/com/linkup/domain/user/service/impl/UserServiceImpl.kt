package com.linkup.domain.user.service.impl

import com.linkup.domain.friend.repository.FriendRepository
import com.linkup.domain.friend.repository.FriendRequestRepository
import com.linkup.domain.user.dto.response.GetUserResponse
import com.linkup.domain.user.dto.response.UserResponse
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.domain.user.service.UserService
import com.linkup.global.error.CustomException
import com.linkup.global.security.holder.SecurityHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val securityHolder: SecurityHolder,
    private val friendRepository: FriendRepository,
    private val friendRequestRepository: FriendRequestRepository
) : UserService {
    @Transactional(readOnly = true)
    override fun getUser(linkupId: String?, phoneNumber: String?): GetUserResponse {
        val user = if (linkupId != null) {
            userRepository.findByLinkupId(linkupId) ?: throw CustomException(UserError.USER_NOT_FOUND)
        } else if (phoneNumber != null) {
            userRepository.findByPhoneNumber(phoneNumber) ?: throw CustomException(UserError.USER_NOT_FOUND)
        } else {
            throw CustomException(UserError.USER_NOT_FOUND)
        }

        val me = securityHolder.user

        return GetUserResponse(
            nickname = user.nickname,
            profileImage = user.profileImage,
            linkupId = user.linkupId,
            isFriend = friendRepository.existsByUserAndFriend(me, user),
            isFriendRequestSent = friendRequestRepository.existsByRequesterAndRequestee(me, user),
        )
    }

    @Transactional(readOnly = true)
    override fun getUserByLinkupId(linkupId: String): UserResponse {
        val user = userRepository.findByLinkupId(linkupId) ?: throw CustomException(UserError.USER_NOT_FOUND)

        return UserResponse.of(user)
    }
}