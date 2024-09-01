package com.linkup.domain.user.dto.request

import com.linkup.domain.user.domain.enums.UserGender
import java.time.LocalDate

data class UserUpdateRequest(
    val nickname: String?,
    val linkupId: String?,
    val birthday: LocalDate?,
    val statusMessage: String?,
    val profileImage: String?,
    val gender: UserGender?,

    val password: String?,
    val currentPassword: String?
)
