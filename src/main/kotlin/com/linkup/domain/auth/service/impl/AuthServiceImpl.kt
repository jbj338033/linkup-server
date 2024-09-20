package com.linkup.domain.auth.service.impl

import com.linkup.domain.auth.dto.request.LoginRequest
import com.linkup.domain.auth.dto.request.ReissueRequest
import com.linkup.domain.auth.dto.request.SignUpRequest
import com.linkup.domain.auth.repository.RefreshTokenRepository
import com.linkup.domain.auth.service.AuthService
import com.linkup.domain.user.domain.entity.User
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.error.CustomException
import com.linkup.global.security.jwt.dto.JwtResponse
import com.linkup.global.security.jwt.enums.JwtType
import com.linkup.global.security.jwt.error.JwtError
import com.linkup.global.security.jwt.provider.JwtProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AuthServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository
) : AuthService {
    @Transactional
    override fun login(request: LoginRequest): JwtResponse {
        val user = userRepository.findByEmail(request.email) ?: throw CustomException(
            UserError.USER_NOT_FOUND_BY_EMAIL,
            request.email
        )

        if (!passwordEncoder.matches(
                request.password,
                user.password
            )
        ) throw CustomException(UserError.PASSWORD_NOT_MATCH)

        return jwtProvider.generateToken(user.email)
    }

    @Transactional
    override fun signup(request: SignUpRequest) {
        if (userRepository.existsByEmail(request.email)) throw CustomException(UserError.EMAIL_DUPLICATED)
        if (userRepository.existsByPhoneNumber(request.phoneNumber)) throw CustomException(UserError.PHONE_NUMBER_DUPLICATED)

        val now = LocalDate.now()

        if (!request.birthday.isBefore(now)) throw CustomException(UserError.INVALID_BIRTHDAY)

        val user = User(
            nickname = request.nickname,
            linkupId = request.linkupId,
            linkupIdUpdatedAt = LocalDateTime.now(),
            email = request.email,
            password = passwordEncoder.encode(request.password),
            birthday = request.birthday,
            phoneNumber = request.phoneNumber,
            gender = request.gender,
        )

        userRepository.save(user)
    }

    @Transactional
    override fun reissue(request: ReissueRequest): JwtResponse {
        if (jwtProvider.getType(request.refreshToken) != JwtType.REFRESH) throw CustomException(JwtError.INVALID_TOKEN_TYPE)

        val email = jwtProvider.getEmail(request.refreshToken)
        val user = userRepository.findByEmail(email) ?: throw CustomException(UserError.USER_NOT_FOUND_BY_EMAIL, email)
        val refreshToken =
            refreshTokenRepository.findByIdOrNull(user.email) ?: throw CustomException(JwtError.INVALID_TOKEN)

        if (refreshToken.refreshToken != request.refreshToken) throw CustomException(JwtError.INVALID_TOKEN)

        return jwtProvider.generateToken(user.email)
    }

    @Transactional(readOnly = true)
    override fun checkEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    @Transactional(readOnly = true)
    override fun checkLinkupId(linkupId: String): Boolean {
        return userRepository.existsByLinkupId(linkupId)
    }

    @Transactional(readOnly = true)
    override fun checkPhoneNumber(phoneNumber: String): Boolean {
        return userRepository.existsByPhoneNumber(phoneNumber)
    }
}