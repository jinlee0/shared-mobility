package com.sharedmobility.api.global.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(RestException::class)
    fun handleRestException(ex: RestException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.info("{} from {}", ex.code, request.requestURI.toString())
        return ResponseEntity.status(ex.code.status)
            .body(ErrorResponse(ex.code, request.requestURI))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.info("{}", ex.message)
        return handleRestException(RestException(ErrorCode.InvalidApiArgument), request)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthException(
        ex: AuthenticationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.info("{}", ex.message)
        return handleRestException(
            RestException(
                when (ex) {
                    is BadCredentialsException -> ErrorCode.WrongPassword
                    is UsernameNotFoundException -> ErrorCode.EmailNotFoundOnAuth
                    else -> ErrorCode.InternalServerError
                }
            ),
            request
        )
    }
}