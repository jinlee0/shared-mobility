package com.sharedmobility.api.global.security

import com.sharedmobility.api.global.exception.ErrorCode
import com.sharedmobility.api.global.exception.RestException
import com.sharedmobility.api.service.customer.repository.CustomerRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailServiceImpl(
    private val customerRepository: CustomerRepository
) : UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails =
        customerRepository.findByEmail(username)?.toUserDetails() ?: throw RestException(ErrorCode.UsernameNotFound)
}