package com.linkup.global.error

import org.springframework.http.HttpStatus

interface CustomError {
    val status: HttpStatus
    val message: String
}