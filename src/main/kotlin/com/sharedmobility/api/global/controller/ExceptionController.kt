package com.sharedmobility.api.global.controller

import com.sharedmobility.api.global.exception.ErrorCode
import com.sharedmobility.api.global.exception.ErrorResponse
import com.sharedmobility.api.global.exception.RestException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExceptionController(
) {
    @GetMapping("\${api.exception.entry-point}")
    fun handleEntryPoint() : ResponseEntity<ErrorResponse> {
        throw RestException(ErrorCode.UnAuthorized)
    }

    @GetMapping("\${api.exception.access-denied-handler}")
    fun handleAccessDeniedHandler() {
        throw RestException(ErrorCode.Forbidden)
    }
}