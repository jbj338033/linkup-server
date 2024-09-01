package com.linkup.domain.user.error

import com.linkup.global.error.CustomError
import org.springframework.http.HttpStatus

enum class UserError(override val status: HttpStatus, override val message: String) : CustomError {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    PHONE_NUMBER_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 사용중인 전화번호입니다."),
    INVALID_BIRTHDAY(HttpStatus.BAD_REQUEST, "유효하지 않은 생년월일입니다."),
    LINKUP_ID_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 사용중인 링크업 아이디입니다.")
}