package com.kakaotalk.domain.auth.dto.request

import com.kakaotalk.domain.user.domain.enums.UserGender
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class SignUpRequest(
    @field:NotBlank
    val nickname: String,

    @field:NotBlank
    val linkupId: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank
    val birthday: LocalDate,

    @field:NotBlank
    val phoneNumber: String,

    @field:NotBlank
    val gender: UserGender
)
