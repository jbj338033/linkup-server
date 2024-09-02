package com.linkup.domain.me.dto.response

import java.time.LocalDateTime

data class MeCanChangeLinkupIdResponse(
    val canChange: Boolean,
    val availableAt: LocalDateTime
)