package com.sharedmobility.api.global.security

import com.sharedmobility.api.service.customer.entity.Customer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    private val username: String,
    private val password: String,
    private val role: SecurityRole,
    private val accountNotExpired: Boolean = true,
    private val accountNotLocked: Boolean = true,
    private val credentialsNonExpired: Boolean = true,
    private val enabled: Boolean = true,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role.name))

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = accountNotExpired

    override fun isAccountNonLocked(): Boolean = accountNotLocked

    override fun isCredentialsNonExpired(): Boolean = credentialsNonExpired

    override fun isEnabled(): Boolean = enabled
}

fun Customer.toUserDetails(): UserDetails =
    UserDetailsImpl(
        username = this.id.toString(),
        password = this.password,
        role = SecurityRole.ROLE_CUSTOMER,
    )
