package com.sharedmobility.api.global.exception

class RestException(
    val code: ErrorCode
) : RuntimeException()
