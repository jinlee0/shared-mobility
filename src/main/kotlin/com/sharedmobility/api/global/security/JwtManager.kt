package com.sharedmobility.api.global.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Component
class JwtManager(
    @Value("\${jwt.secret}")
    val secret: String,
    @Value("\${jwt.access-token-expired-millis}")
    val accessTokenExpiredMillis: Long,
    @Value("\${jwt.refresh-token-expired-millis}")
    val refreshTokenExpiredMillis: Long,
) : InitializingBean {
    companion object {
        const val AUTH_KEY = "auth"
        const val BEARER = "Bearer"
    }

    private val log = LoggerFactory.getLogger(this::class.java)

    lateinit var key: Key

    override fun afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    }

    fun generateAccessToken(authentication: Authentication): String {
        return generateToken(authentication, accessTokenExpiredMillis)
    }

    fun generateRefreshToken(authentication: Authentication): String {
        return generateToken(authentication, refreshTokenExpiredMillis)
    }

    private fun generateToken(authentication: Authentication, expiredMillis: Long): String {
        val authorities =
            authentication.authorities.joinToString(separator = ",", transform = GrantedAuthority::getAuthority)
        val now = Date().time
        val validity = Date(now + expiredMillis)
        val token = Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTH_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
        return "$BEARER $token"
    }

    fun valid(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: MalformedJwtException) {
            log.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            log.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        val authorities = Arrays.stream(claims[AUTH_KEY].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
            .map { role: String? ->
                SimpleGrantedAuthority(
                    role
                )
            }
            .collect(Collectors.toList())
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }
}
