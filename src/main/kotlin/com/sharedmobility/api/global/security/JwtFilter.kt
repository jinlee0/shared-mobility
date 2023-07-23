package com.sharedmobility.api.global.security

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class JwtFilter(
    private val jwtManager: JwtManager
) : Filter {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpServletRequest = request as HttpServletRequest
        val jwt = resolveToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
        val requestURI = httpServletRequest.requestURI
        if (StringUtils.hasText(jwt) && jwtManager.valid(jwt!!)) {
            val authentication = jwtManager.getAuthentication(jwt) // 정상 토큰이면 SecurityContext 저장
            SecurityContextHolder.getContext().authentication = authentication
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.name, requestURI)
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI)
        }
        chain.doFilter(request, response)
    }

    /**
     * 토큰이 다름 조건을 만족하는 경우 정제하여 반환한다.
     * 1. StringUtils.hasText(token): token != null && token.trim().length > 0
     * 2. token.startsWith("Bearer ")
     * @param token
     * @return
     */
    private fun resolveToken(token: String?): String? {
        return if (StringUtils.hasText(token) && token!!.startsWith("Bearer ")) {
            token.substring(7)
        } else null
    }

}