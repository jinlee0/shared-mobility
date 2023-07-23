package com.sharedmobility.api.service.customer.entity

import com.sharedmobility.api.global.entity.PkEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "customer")
class Customer(
    email: String,
    password: String,
) : PkEntity() {
    @Column(name = "email", nullable = false, unique = true)
    var email: String = email
        protected set

    @Column(name = "password", nullable = false)
    var password: String = password
        protected set
}