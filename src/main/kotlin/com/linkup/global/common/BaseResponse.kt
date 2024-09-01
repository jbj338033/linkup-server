package com.linkup.global.common

import org.springframework.http.ResponseEntity

data class BaseResponse<T>(
    val data: T,
    val status: Int,
    val message: String,
    val success: Boolean
) {
    companion object {
        fun of(data: Any, status: Int = 200, message: String = "success", success: Boolean = true) =
            ResponseEntity.status(status).body(BaseResponse(data, status, message, success))
    }
}