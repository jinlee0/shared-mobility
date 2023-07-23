package com.sharedmobility.api.service.auth.appservice

interface AuthService {
    fun authenticate(dto: SvAuthenticationDto): TokenDto
}