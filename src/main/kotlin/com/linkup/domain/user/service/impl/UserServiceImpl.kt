package com.kakaotalk.domain.user.service.impl

import com.kakaotalk.domain.user.dto.request.UserUpdateRequest
import com.kakaotalk.domain.user.dto.response.UserResponse
import com.kakaotalk.domain.user.error.UserError
import com.kakaotalk.domain.user.repository.UserRepository
import com.kakaotalk.domain.user.service.UserService
import com.kakaotalk.global.error.CustomException
import com.kakaotalk.global.security.holder.SecurityHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val securityHolder: SecurityHolder,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserService {
    @Transactional(readOnly = true)
    override fun getMe() = UserResponse(securityHolder.user)

    @Transactional
    override fun updateMe(request: UserUpdateRequest): UserResponse {
        val user = securityHolder.user

        user.nickname = request.nickname ?: user.nickname
        user.linkupId = request.linkupId ?: user.linkupId
        user.birthday = request.birthday ?: user.birthday
        user.statusMessage = request.statusMessage ?: user.statusMessage
        user.profileImage = request.profileImage ?: user.profileImage
        user.gender = request.gender ?: user.gender

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