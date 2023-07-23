package com.sharedmobility.api.service.customer.appservice

import com.sharedmobility.api.service.customer.entity.Customer
import java.time.LocalDateTime

data class SvCustomerDto(
    val email: String,
    val createdAt: LocalDateTime
)

fun Customer.toSvDto(): SvCustomerDto = SvCustomerDto(this.email, this.createdAt)

data class SvSignupDto(
    val email: String,
    val password: String,
)