package com.sharedmobility.api.service.auth.controller

import com.sharedmobility.api.service.auth.appservice.AuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
    @Value("\${headers.access-token}")
    private val accessTokenHeaderKey: String,
    @Value("\${headers.refresh-token}")
    private val refreshTokenHeaderKey: String,
) {
    @PostMapping("\${api.auth.authenticate}")
    fun authenticate(
        @RequestBody
        @Valid
        dto: CoAuthenticationDto,
    ): ResponseEntity<Void> =
        authService.authenticate(dto.toServiceDto())
            .let {tokenDto ->
                ResponseEntity.ok().headers { h ->
                    h.add(accessTokenHeaderKey, tokenDto.accessToken)
                    h.add(refreshTokenHeaderKey, tokenDto.refreshToken)
                }.build()
            }
}
