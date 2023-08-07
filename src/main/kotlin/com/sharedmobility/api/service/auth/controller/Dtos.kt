package com.sharedmobility.api.service.auth.controller

import com.sharedmobility.api.service.auth.appservice.SvAuthenticationDto
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

class CoAuthenticationDto(
    @field:Email
    val email: String,
    @field:Size(min = 4, max = 20)
    val password: String,
) {
    fun toServiceDto(): SvAuthenticationDto = SvAuthenticationDto(email, password)
}
