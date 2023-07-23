package com.sharedmobility.api.global.exception

class ErrorResponse(
    errorCode: ErrorCode,
    val requestUri: String,
) {
    val code = errorCode.name
}