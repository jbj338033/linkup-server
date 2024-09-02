package com.linkup.domain.user.service.impl

import com.linkup.domain.user.dto.response.GetUserResponse
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
    private val securityHolder: SecurityHolder
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

        return GetUserResponse.of(user, securityHolder.user)
    }
}