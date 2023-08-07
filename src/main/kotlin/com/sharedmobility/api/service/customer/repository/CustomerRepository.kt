package com.sharedmobility.api.service.customer.repository

import com.sharedmobility.api.service.customer.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CustomerRepository : JpaRepository<Customer, UUID> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Customer?
}
