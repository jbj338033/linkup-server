package com.linkup.domain.friend.service.impl

import com.linkup.domain.friend.domain.entity.Friend
import com.linkup.domain.friend.domain.entity.FriendRequest
import com.linkup.domain.friend.error.FriendError
import com.linkup.domain.friend.repository.FriendRepository
import com.linkup.domain.friend.repository.FriendRequestRepository
import com.linkup.domain.friend.service.FriendRequestService
import com.linkup.domain.user.dto.response.UserResponse
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.error.CustomException
import com.linkup.global.security.holder.SecurityHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FriendRequestServiceImpl(
    private val securityHolder: SecurityHolder,
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
    private val friendRequestRepository: FriendRequestRepository
) : FriendRequestService {
    @Transactional(readOnly = true)
    override fun getFriendRequests(): List<UserResponse> {
        val user = securityHolder.user

        return friendRequestRepository.findAllByRequestee(user).map { UserResponse.of(it.requester) }
    }

    @Transactional(readOnly = true)
    override fun getSentFriendRequests(): List<UserResponse> {
        val user = securityHolder.user

        return friendRequestRepository.findAllByRequester(user).map { UserResponse.of(it.requestee) }
    }

    @Transactional
    override fun sendFriendRequest(linkupId: String?, phoneNumber: String?) {
        val requester = securityHolder.user

        val requestee = if (linkupId != null) {
            userRepository.findByLinkupId(linkupId) ?: throw CustomException(
                UserError.USER_NOT_FOUND_BY_LINKUP_ID,
                linkupId
            )
        } else if (phoneNumber != null) {
            userRepository.findByPhoneNumber(phoneNumber)
                ?: throw CustomException(UserError.USER_NOT_FOUND_BY_PHONE_NUMBER, phoneNumber)
        } else {
            throw CustomException(UserError.LINKUP_ID_OR_PHONE_NUMBER_IS_NULL)
        }

        if (requester == requestee) throw CustomException(FriendError.FRIEND_REQUEST_SELF)

        val isFriend = friendRepository.existsByUserAndFriend(requester, requestee)

        if (isFriend) throw CustomException(FriendError.FRIEND_ALREADY_EXIST)

        val isRequested = friendRequestRepository.existsByRequesterAndRequestee(requester, requestee)

        if (isRequested) throw CustomException(FriendError.FRIEND_REQUEST_ALREADY_EXIST)

        val hasRequested = friendRequestRepository.existsByRequesterAndRequestee(requestee, requester)

        if (hasRequested) {
            friendRepository.save(Friend(user = requester, friend = requestee))
            friendRepository.save(Friend(user = requestee, friend = requester))

            friendRequestRepository.deleteByRequesterAndRequestee(requestee, requester)

            return
        }

        friendRequestRepository.save(FriendRequest(requester = requester, requestee = requestee))
    }

    @Transactional
    override fun acceptFriendRequest(linkupId: String) {
        val requester = userRepository.findByLinkupId(linkupId)
            ?: throw CustomException(UserError.USER_NOT_FOUND_BY_LINKUP_ID, linkupId)
        val requestee = securityHolder.user

        val isRequested = friendRequestRepository.existsByRequesterAndRequestee(requester, requestee)

        if (!isRequested) throw CustomException(FriendError.FRIEND_REQUEST_NOT_EXIST)

        friendRepository.save(Friend(user = requestee, friend = requester))
        friendRepository.save(Friend(user = requester, friend = requestee))

        friendRequestRepository.deleteByRequesterAndRequestee(requester, requestee)
    }

    @Transactional
    override fun rejectFriendRequest(linkupId: String) {
        val requester = userRepository.findByLinkupId(linkupId)
            ?: throw CustomException(UserError.USER_NOT_FOUND_BY_LINKUP_ID, linkupId)
        val requestee = securityHolder.user

        val isRequested = friendRequestRepository.existsByRequesterAndRequestee(requester, requestee)

        if (!isRequested) throw CustomException(FriendError.FRIEND_REQUEST_NOT_EXIST)

        friendRequestRepository.deleteByRequesterAndRequestee(requester, requestee)
    }

    @Transactional
    override fun cancelFriendRequest(linkupId: String) {
        val requester = securityHolder.user
        val requestee = userRepository.findByLinkupId(linkupId)
            ?: throw CustomException(UserError.USER_NOT_FOUND_BY_LINKUP_ID, linkupId)

        val isRequested = friendRequestRepository.existsByRequesterAndRequestee(requester, requestee)

        if (!isRequested) throw CustomException(FriendError.FRIEND_REQUEST_NOT_EXIST)

        friendRequestRepository.deleteByRequesterAndRequestee(requester, requestee)
    }
}