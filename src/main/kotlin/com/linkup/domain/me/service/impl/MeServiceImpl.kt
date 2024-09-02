package com.linkup.domain.me.service.impl

import com.linkup.domain.me.dto.request.MeUpdateRequest
import com.linkup.domain.me.dto.response.MeCanChangeLinkupIdResponse
import com.linkup.domain.me.service.MeService
import com.linkup.domain.user.dto.response.UserResponse
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.error.CustomException
import com.linkup.global.security.holder.SecurityHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class MeServiceImpl(
    private val securityHolder: SecurityHolder,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : MeService {
    @Transactional(readOnly = true)
    override fun getMe() = UserResponse.of(securityHolder.user)

    @Transactional
    override fun updateMe(request: MeUpdateRequest): UserResponse {
        val user = securityHolder.user

        user.nickname = request.nickname ?: user.nickname
        user.statusMessage = request.statusMessage ?: user.statusMessage
        user.profileImage = request.profileImage ?: user.profileImage
        user.gender = request.gender ?: user.gender

        if (request.linkupId != null) {
            if (userRepository.existsByLinkupId(request.linkupId))
                throw CustomException(UserError.LINKUP_ID_DUPLICATED)

            val now = LocalDateTime.now()

            if (!user.linkupIdUpdatedAt.plusDays(30).isBefore(now))
                throw CustomException(UserError.LINKUP_ID_UPDATED_AT)

            user.linkupId = request.linkupId
            user.linkupIdUpdatedAt = now
        }

        if (request.birthday != null) {
            if (!request.birthday.isBefore(LocalDate.now()))
                throw CustomException(UserError.INVALID_BIRTHDAY)

            user.birthday = request.birthday
        }

        if (request.password != null && request.currentPassword != null) {
            if (!passwordEncoder.matches(request.currentPassword, user.password))
                throw CustomException(UserError.PASSWORD_NOT_MATCH)

            user.password = passwordEncoder.encode(request.password)
        }

        return UserResponse.of(userRepository.save(user))
    }

    @Transactional(readOnly = true)
    override fun canChangeLinkupId(): MeCanChangeLinkupIdResponse {
        val user = securityHolder.user
        val availableAt = user.linkupIdUpdatedAt.plusDays(30)

        return MeCanChangeLinkupIdResponse(
            canChange = availableAt.isBefore(LocalDateTime.now()),
            availableAt = availableAt
        )
    }
}