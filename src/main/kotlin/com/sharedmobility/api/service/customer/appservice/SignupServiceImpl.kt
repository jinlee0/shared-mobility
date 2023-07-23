package com.sharedmobility.api.service.customer.appservice

import com.sharedmobility.api.global.exception.ErrorCode
import com.sharedmobility.api.global.exception.RestException
import com.sharedmobility.api.service.customer.entity.Customer
import com.sharedmobility.api.service.customer.repository.CustomerRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignupServiceImpl(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder
) : SignupService {
    @Transactional
    override fun signup(dto: SvSignupDto): SvCustomerDto =
        if (customerRepository.existsByEmail(dto.email)) {
            throw RestException(ErrorCode.EmailAlreadyExists)
        } else {
            customerRepository.save(
                Customer(
                    email = dto.email,
                    password = passwordEncoder.encode(dto.password)
                )
            ).toSvDto()
        }

    override fun existsBy(email: String): Boolean = customerRepository.existsByEmail(email)
}