package com.sharedmobility.api.service.customer.controller

import com.sharedmobility.api.service.customer.appservice.SignupService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SignupController(
    private val signupService: SignupService
) {
    @PostMapping("\${api.customer.signup}")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestBody
        @Valid
        dto: CoSignupRequestDto
    ): CoSignupResponseDto =
        signupService.signup(dto.toServiceDto()).toCoSignupResponseDto()

    @GetMapping("\${api.customer.exists_by_email}")
    @ResponseStatus(HttpStatus.OK)
    fun existsByEmail(email: String): Boolean = signupService.existsBy(email)
}