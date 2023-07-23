package com.sharedmobility.api.service.customer.appservice

interface SignupService {
    fun signup(dto: SvSignupDto): SvCustomerDto

    fun existsBy(email: String): Boolean
}