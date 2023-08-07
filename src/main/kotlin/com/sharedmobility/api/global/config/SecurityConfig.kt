package com.sharedmobility.api.global.config

import com.sharedmobility.api.global.security.JwtAccessDeniedHandler
import com.sharedmobility.api.global.security.JwtAuthenticationEntryPoint
import com.sharedmobility.api.global.security.JwtSecurityConfigAdapter
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@EnableMethodSecurity(proxyTargetClass = true)
@Configuration
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtSecurityConfigAdapter: JwtSecurityConfigAdapter,
    @Value("\${api.pub}")
    private val publicPaths: Array<String>,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun postConstruct() {
        log.info("public paths: {}", publicPaths.contentToString())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web -> web.ignoring().requestMatchers(*publicPaths) }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val httpSecurity = http
            .csrf { obj -> obj.disable() }
            .cors { obj -> obj.disable() }
            .exceptionHandling { c ->
                c.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler)
            }
            .headers { headerConfig -> headerConfig.frameOptions { it.sameOrigin() } }
            .sessionManagement { sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { req ->
                req.requestMatchers(*publicPaths).permitAll().anyRequest().authenticated()
            }
        httpSecurity.apply(jwtSecurityConfigAdapter)
        return httpSecurity.build()
    }
}
