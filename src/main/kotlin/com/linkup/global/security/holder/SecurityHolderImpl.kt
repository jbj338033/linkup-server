package com.kakaotalk.global.security.holder

import com.kakaotalk.domain.user.domain.entity.User
import com.kakaotalk.domain.user.error.UserError
import com.kakaotalk.domain.user.repository.UserRepository
import com.kakaotalk.global.error.CustomException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityHolderImpl(
    private val userRepository: UserRepository
): SecurityHolder {
    override val user: User
        get() = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name) ?: throw CustomException(UserError.USER_NOT_FOUND)
}