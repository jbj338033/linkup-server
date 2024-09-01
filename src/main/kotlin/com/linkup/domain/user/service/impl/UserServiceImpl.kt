package com.linkup.domain.user.service.impl

import com.linkup.domain.user.dto.request.UserUpdateRequest
import com.linkup.domain.user.dto.response.UserResponse
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.domain.user.service.UserService
import com.linkup.global.error.CustomException
import com.linkup.global.security.holder.SecurityHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class UserServiceImpl(
    private val securityHolder: SecurityHolder,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    @Transactional(readOnly = true)
    override fun getMe() = UserResponse(securityHolder.user)

    @Transactional
    override fun updateMe(request: UserUpdateRequest): UserResponse {
        val user = securityHolder.user

        user.nickname = request.nickname ?: user.nickname
        user.statusMessage = request.statusMessage ?: user.statusMessage
        user.profileImage = request.profileImage ?: user.profileImage
        user.gender = request.gender ?: user.gender

        if (request.linkupId != null) {
            if (!userRepository.existsByLinkupId(request.linkupId)) {
                user.linkupId = request.linkupId
            } else {
                throw CustomException(UserError.LINKUP_ID_DUPLICATED)
            }
        }

        if (request.birthday != null) {
            if (request.birthday.isBefore(LocalDate.now())) {
                user.birthday = request.birthday
            } else {
                throw CustomException(UserError.INVALID_BIRTHDAY)
            }
        }

        if (request.password != null && request.currentPassword != null) {
            if (passwordEncoder.matches(request.currentPassword, user.password)) {
                user.password = passwordEncoder.encode(request.password)
            } else {
                throw CustomException(UserError.PASSWORD_NOT_MATCH)
            }
        }

        return UserResponse(userRepository.save(user))
    }
}