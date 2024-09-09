package com.linkup.global.security.holder

import com.linkup.domain.user.domain.entity.User
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.error.CustomException
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityHolderImpl(
    private val userRepository: UserRepository
) : SecurityHolder {
    override val email: String
        get() = if (isWebSocket) SimpAttributesContextHolder.currentAttributes()
            .getAttribute("email") as String else SecurityContextHolder.getContext().authentication.name
    override val user: User
        get() = userRepository.findByEmail(email) ?: throw CustomException(UserError.USER_NOT_FOUND)

    private val isWebSocket: Boolean
        get() = SimpAttributesContextHolder.currentAttributes().getAttribute("email") != null
}