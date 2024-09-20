package com.linkup.domain.chat.room.error

import com.linkup.global.error.CustomError
import org.springframework.http.HttpStatus

enum class ChatRoomError(override val status: HttpStatus, override val message: String) : CustomError {
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
    CHAT_ROOM_NOT_OPEN(HttpStatus.BAD_REQUEST, "공개 채팅방이 아닙니다."),
    CHAT_ROOM_ALREADY_JOINED(HttpStatus.BAD_REQUEST, "이미 참여한 채팅방입니다."),
    CHAT_ROOM_NOT_JOINED(HttpStatus.BAD_REQUEST, "참여하지 않은 채팅방입니다."),
    NOT_PARTICIPANT(HttpStatus.BAD_REQUEST, "참여자가 아닙니다."),
    CANNOT_CREATE_PERSONAL_CHAT_ROOM_WITH_MYSELF(HttpStatus.BAD_REQUEST, "자신과의 개인 채팅방을 생성할 수 없습니다."),
}
