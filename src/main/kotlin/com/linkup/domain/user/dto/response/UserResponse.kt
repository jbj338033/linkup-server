package com.linkup.domain.user.dto.response

import com.linkup.domain.user.domain.entity.User
import com.linkup.domain.user.domain.enums.UserGender
import com.linkup.domain.user.domain.enums.UserRole
import java.time.LocalDate

data class UserResponse(
    val nickname: String,
    val linkupId: String,
    val email: String,
    val birthday: LocalDate,
    val phoneNumber: String,
    val statusMessage: String,
    val profileImage: String,
    val role: UserRole,
    val gender: UserGender
) {
    constructor(user: User) : this(
        nickname = user.nickname,
        linkupId = user.linkupId,
        email = user.email,
        birthday = user.birthday,
        phoneNumber = user.phoneNumber,
        statusMessage = user.statusMessage,
        profileImage = user.profileImage,
        role = user.role,
        gender = user.gender
    )
}