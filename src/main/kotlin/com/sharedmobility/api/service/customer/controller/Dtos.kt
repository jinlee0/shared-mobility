package com.sharedmobility.api.service.customer.controller

import com.sharedmobility.api.service.customer.appservice.SvCustomerDto
import com.sharedmobility.api.service.customer.appservice.SvSignupDto
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CoSignupRequestDto(
    @field:Email
    val email: String,
    @field:Size(min = 4, max = 20)
    val password: String,
) {
    fun toServiceDto(): SvSignupDto = SvSignupDto(email, password)
}

data class CoSignupResponseDto(
    val email: String,
    val createdAt: LocalDateTime,
)

fun SvCustomerDto.toCoSignupResponseDto() = CoSignupResponseDto(email = this.email, createdAt = this.createdAt)

