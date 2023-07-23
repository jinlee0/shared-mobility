package com.sharedmobility.api.service.auth.appservice

import com.sharedmobility.api.global.exception.ErrorCode
import com.sharedmobility.api.global.exception.RestException
import com.sharedmobility.api.global.security.JwtManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val jwtManager: JwtManager,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
) : AuthService {
    override fun authenticate(dto: SvAuthenticationDto): TokenDto {
        try {
            val authToken = UsernamePasswordAuthenticationToken(dto.email, dto.password)
            val authenticate = authenticationManagerBuilder.`object`.authenticate(authToken)
            return TokenDto(
                accessToken = jwtManager.generateAccessToken(authenticate),
                refreshToken = jwtManager.generateRefreshToken(authToken)
            )
        } catch (e: BadCredentialsException) {
            throw RestException(ErrorCode.WrongPassword)
        } catch (e: UsernameNotFoundException) {
            throw RestException(ErrorCode.EmailNotFoundOnAuth)
        } catch (e: Exception) {
            throw RestException(ErrorCode.InternalServerError)
        }
    }
}