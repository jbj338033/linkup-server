package com.linkup.domain.friend.error

import com.linkup.global.error.CustomError
import org.springframework.http.HttpStatus

enum class FriendError(override val status: HttpStatus, override val message: String) : CustomError {
    // Request
    FRIEND_REQUEST_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 친구 요청을 보냈습니다."),
    FRIEND_REQUEST_NOT_EXIST(HttpStatus.BAD_REQUEST, "친구 요청이 존재하지 않습니다."),
    FRIEND_REQUEST_SELF(HttpStatus.BAD_REQUEST, "자기 자신에게 친구 요청을 보낼 수 없습니다."),
    FRIEND_NOT_EXIST(HttpStatus.BAD_REQUEST, "친구가 존재하지 않습니다."),
    FRIEND_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 친구입니다."),
}