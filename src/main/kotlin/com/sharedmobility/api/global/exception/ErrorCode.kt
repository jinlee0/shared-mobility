package com.sharedmobility.api.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus
) {
    Forbidden(HttpStatus.FORBIDDEN),
    UnAuthorized(HttpStatus.UNAUTHORIZED),
    InvalidApiArgument(HttpStatus.BAD_REQUEST),
    WrongPassword(HttpStatus.UNAUTHORIZED),
    EmailNotFoundOnAuth(HttpStatus.UNAUTHORIZED),
    InternalServerError(HttpStatus.INTERNAL_SERVER_ERROR),
    UsernameNotFound(HttpStatus.NOT_FOUND),
    EmailAlreadyExists(HttpStatus.BAD_REQUEST),
    ;
}