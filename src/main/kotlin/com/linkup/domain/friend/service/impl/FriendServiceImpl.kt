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
): FriendRequestService {
    @Transactional
    override fun sendFriendRequest(requesteeId: String) {
        val requester = securityHolder.user
        val requestee = userRepository.findByLinkupId(requesteeId)
            ?: throw CustomException(UserError.USER_NOT_FOUND)

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
    override fun acceptFriendRequest(requesterId: String) {
        val requester = userRepository.findByLinkupId(requesterId)
            ?: throw CustomException(UserError.USER_NOT_FOUND)

        val requestee = securityHolder.user

        val isRequested = friendRequestRepository.existsByRequesterAndRequestee(requester, requestee)

        if (!isRequested) throw CustomException(FriendError.FRIEND_REQUEST_NOT_EXIST)

        friendRepository.save(Friend(user = requestee, friend = requester))
        friendRepository.save(Friend(user = requester, friend = requestee))

        friendRequestRepository.deleteByRequesterAndRequestee(requester, requestee)
    }

    @Transactional
    override fun rejectFriendRequest(requesterId: String) {
        val requester = userRepository.findByLinkupId(requesterId)
            ?: throw CustomException(UserError.USER_NOT_FOUND)

        val requestee = securityHolder.user

        val isRequested = friendRequestRepository.existsByRequesterAndRequestee(requester, requestee)

        if (!isRequested) throw CustomException(FriendError.FRIEND_REQUEST_NOT_EXIST)

        friendRequestRepository.deleteByRequesterAndRequestee(requester, requestee)
    }

    @Transactional
    override fun cancelFriendRequest(requesteeId: String) {
        val requester = securityHolder.user
        val requestee = userRepository.findByLinkupId(requesteeId)
            ?: throw CustomException(UserError.USER_NOT_FOUND)

        val isRequested = friendRequestRepository.existsByRequesterAndRequestee(requester, requestee)

        if (!isRequested) throw CustomException(FriendError.FRIEND_REQUEST_NOT_EXIST)

        friendRequestRepository.deleteByRequesterAndRequestee(requester, requestee)
    }

    @Transactional(readOnly = true)
    override fun getFriendRequests(): List<UserResponse> {
        val user = securityHolder.user

        return friendRequestRepository.findAllByRequestee(user).map { UserResponse(it.requester) }
    }

    @Transactional(readOnly = true)
    override fun getFriends(): List<UserResponse> {
        val user = securityHolder.user

        return friendRepository.findAllByUser(user).map { UserResponse(it.friend) }
    }
}