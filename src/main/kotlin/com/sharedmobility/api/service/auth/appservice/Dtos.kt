package com.sharedmobility.api.service.auth.appservice

data class SvAuthenticationDto(
    val email: String,
    val password: String,
)

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
)