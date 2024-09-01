package com.linkup.global.security.holder

import com.linkup.domain.user.domain.entity.User
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.error.CustomException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityHolderImpl(
    private val userRepository: UserRepository
) : SecurityHolder {
    override val user: User
        get() = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)
            ?: throw CustomException(UserError.USER_NOT_FOUND)
}