package com.linkup.domain.me.dto.request

import com.linkup.domain.user.domain.enums.UserGender
import java.time.LocalDate

data class MeUpdateRequest(
    val nickname: String?,
    val linkupId: String?,
    val birthday: LocalDate?,
    val statusMessage: String?,
    val profileImage: String?,
    val gender: UserGender?,

    val password: String?,
    val currentPassword: String?
)
