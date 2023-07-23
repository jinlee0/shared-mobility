package com.sharedmobility.api.service.ticket.entity

import com.sharedmobility.api.global.entity.PkEntity
import com.sharedmobility.api.global.types.TicketType
import com.sharedmobility.api.service.customer.entity.Customer
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.*

@Entity(name = "ticket")
class Ticket(
    @Column(name = "customer_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val customerId: UUID,
    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    val type: TicketType,
    @Column(name = "usable_duration")
    val usableDuration: Int = type.duration.toDays().toInt()
) : PkEntity() {
    @Column(name = "start_at")
    var startAt: LocalDateTime? = null

    @Column(name = "last_used_at")
    var lastUsedAt: LocalDateTime? = null

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.MIN
        protected set
}
