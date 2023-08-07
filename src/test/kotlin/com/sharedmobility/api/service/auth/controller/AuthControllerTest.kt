package com.sharedmobility.api.service.auth.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sharedmobility.api.global.config.SecurityConfig
import com.sharedmobility.api.global.exception.ErrorCode
import com.sharedmobility.api.global.security.JwtAccessDeniedHandler
import com.sharedmobility.api.global.security.JwtAuthenticationEntryPoint
import com.sharedmobility.api.global.security.JwtManager
import com.sharedmobility.api.global.security.JwtSecurityConfigAdapter
import com.sharedmobility.api.service.auth.appservice.AuthServiceImpl
import com.sharedmobility.api.service.auth.appservice.TokenDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [AuthController::class])
@Import(
    SecurityConfig::class,
    JwtAuthenticationEntryPoint::class,
    JwtAccessDeniedHandler::class,
    JwtSecurityConfigAdapter::class,
    JwtManager::class
)
class AuthControllerTest {
    @MockBean
    lateinit var authServiceImpl: AuthServiceImpl

    @Autowired
    lateinit var mvc: MockMvc

    @Value("\${api.auth.authenticate}")
    lateinit var authenticatePath: String

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Value("\${headers.access-token}")
    lateinit var accessTokenHeaderKey: String

    @Value("\${headers.refresh-token}")
    lateinit var refreshTokenHeaderKey: String

    @Test
    fun `authenticate success`() {
        val token = "Bearer sdlfjksdjflskdjf"
        val exp = CoAuthenticationDto(email = "email@fortest.com", password = "password4test")
        given(authServiceImpl.authenticate(exp.toServiceDto()))
            .willReturn(TokenDto(token, token))


        mvc.perform(
            post(authenticatePath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exp))
        )
            .andExpect(status().isOk)
            .andExpect(header().string(accessTokenHeaderKey, token))
            .andExpect(header().string(refreshTokenHeaderKey, token))
    }

    @Test
    fun `wrong req body should return error response`() {
        val token = "Bearer sdlfjksdjflskdjf"
        val exp = CoAuthenticationDto(email = "emailst", password = "1")
        given(authServiceImpl.authenticate(exp.toServiceDto()))
            .willReturn(TokenDto(token, token))

        mvc.perform(
            post(authenticatePath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exp))
        )
            .andExpect(jsonPath("\$.code").value(ErrorCode.InvalidApiArgument.name))
    }

}